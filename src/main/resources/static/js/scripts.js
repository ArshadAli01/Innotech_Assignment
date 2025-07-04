document.getElementById('uploadForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const fileInput = document.getElementById('csvFile');
    const statusDiv = document.getElementById('uploadStatus');

    if (!fileInput.files.length) {
        statusDiv.innerHTML = '<div class="alert alert-danger">Please select a CSV file.</div>';
        return;
    }

    const formData = new FormData();
    formData.append('file', fileInput.files[0]);

    try {
        const response = await fetch('/api/upload-sales-data', {
            method: 'POST',
            body: formData
        });
        const result = await response.json();

        if (response.ok) {
            statusDiv.innerHTML = '<div class="alert alert-success">File uploaded successfully!</div>';
            fileInput.value = ''; // Clear file input
            loadSummaries();
        } else {
            statusDiv.innerHTML = `<div class="alert alert-danger">${result.message || 'Error uploading file'}</div>`;
        }
    } catch (error) {
        statusDiv.innerHTML = '<div class="alert alert-danger">Error: ' + error.message + '</div>';
    }
});

async function loadSummaries() {
    try {
        const response = await fetch('/api/sales-summaries');
        const summaries = await response.json();
        const tbody = document.getElementById('summariesBody');
        tbody.innerHTML = '';

        summaries.forEach(summary => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${summary.id}</td>
                <td>${new Date(summary.uploadTimestamp).toLocaleString()}</td>
                <td>$${summary.totalRevenue.toFixed(2)}</td>
                <td><button class="btn btn-info btn-sm" onclick="showSummaryDetails('${summary.id}')">Details</button></td>
            `;
            tbody.appendChild(row);
        });
    } catch (error) {
        console.error('Error loading summaries:', error);
    }
}

async function showSummaryDetails(id) {
    try {
        const response = await fetch('/api/sales-summaries');
        const summaries = await response.json();
        const summary = summaries.find(s => s.id === id);
        if (summary) {
            const modalBody = document.getElementById('modalBody');
            modalBody.innerHTML = `
                <p><strong>Upload ID:</strong> ${summary.id}</p>
                <p><strong>Timestamp:</strong> ${new Date(summary.uploadTimestamp).toLocaleString()}</p>
                <p><strong>Total Records:</strong> ${summary.totalRecords}</p>
                <p><strong>Total Quantity:</strong> ${summary.totalQuantity}</p>
                <p><strong>Total Revenue:</strong> $${summary.totalRevenue.toFixed(2)}</p>
            `;
            const modal = new bootstrap.Modal(document.getElementById('summaryModal'));
            modal.show();
        }
    } catch (error) {
        console.error('Error loading summary details:', error);
    }
}

// Load summaries on page load
document.addEventListener('DOMContentLoaded', loadSummaries);