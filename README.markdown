# CSV Sales Data Uploader & Dynamic Summary Dashboard

## Project Overview

This web application allows users to upload CSV files containing sales data and view summarized metrics on a dynamic dashboard. The application processes uploaded CSV files with a strict three-column format—`product_name`, `quantity`, and `price_per_unit`—and calculates key metrics: total records (number of rows), total quantity (sum of quantities), and total revenue (sum of quantity × price_per_unit per row). These metrics are stored in an in-memory list for the duration of the server session. The dashboard displays a list of all processed uploads, showing each upload's unique ID, timestamp, and total revenue, with an option to view detailed metrics (total records, total quantity, and total revenue) by clicking an entry.

## Setup Instructions

To set up and run this application locally, follow these steps:

1. **Prerequisites**:
   - Install Java Development Kit (JDK) 17 or later.
   - Install Maven for building and managing the project.
   - Use a modern web browser (e.g., Chrome, Firefox).

2. **Clone the Repository**:
   - Clone the project repository using:
     ```
     git clone https://github.com/yourusername/csv-sales-uploader.git
     ```
   - Alternatively, download and extract the project files manually.

3. **Build the Project**:
   - Navigate to the project directory:
     ```
     cd csv-sales-uploader
     ```
   - Build the project with Maven:
     ```
     mvn clean install
     ```

4. **Run the Application**:
   - Launch the Spring Boot application:
     ```
     mvn spring-boot:run
     ```
   - Alternatively, run it from your IDE by executing the main application class.

5. **Access the Application**:
   - Open your browser and go to `http://localhost:8080`.
   - You’ll see a file upload form and a dashboard for viewing summaries.

6. **Upload a CSV File**:
   - Prepare a CSV file with the headers: `product_name,quantity,price_per_unit` (e.g., `Laptop,2,1200.00`).
   - Upload the file using the form and verify the dashboard updates with the summary.

## Tech Stack

The application leverages a straightforward tech stack for both frontend and backend functionality:

### Backend
- **Java**: The core language, chosen for its reliability and rich ecosystem, handles data processing and server-side logic.
- **Spring Boot**: A framework that simplifies backend development with an embedded Tomcat server, dependency injection, and RESTful API creation. It powers the two key endpoints: file upload and summary retrieval.
- **Maven**: Manages dependencies and builds the project into an executable JAR, streamlining development and deployment.
- **OpenCSV**: A lightweight library for parsing CSV files, used to read and process the uploaded sales data efficiently.

### Frontend
- **HTML**: Structures the file upload form and dashboard table.
- **CSS**: Styles the interface for a clean, responsive design.
- **JavaScript**: Adds interactivity, handling file uploads and dynamically updating the dashboard with summary data.
- **Bootstrap**: A CSS framework that ensures a mobile-friendly, consistent UI with minimal custom styling.

### In-Memory Storage
- A simple Java list (or map) stores summary objects in memory, each containing the upload ID, timestamp, and calculated metrics. This approach keeps the application lightweight but limits data persistence to the server session.

## In-Depth Explanation

### Frontend
- **File Upload Form**: An HTML `<input type="file">` with an "Upload" button sends the CSV file to the backend via a POST request.
- **Upload Status Feedback**: Post-upload, the UI shows a success message or an error if parsing fails (e.g., incorrect CSV format).
- **Dynamic Dashboard**: A table lists all summaries with columns for Upload ID, Timestamp, and Total Revenue. Clicking a row reveals a detailed view (e.g., via a modal) with total records, total quantity, and total revenue.

### Backend
- **Endpoints**:
  - `POST /upload-sales-data`: Accepts a CSV file, parses it row-by-row, computes metrics, and stores the summary in memory. Returns JSON with success or error status.
  - `GET /sales-summaries`: Returns the list of all summary objects as JSON for the dashboard.
- **Data Storage**: An in-memory list holds summary objects, each with a unique ID, timestamp, and metrics. Data is volatile and lost on server restart.

### CSV Format
- The CSV must have exactly three columns: `product_name,quantity,price_per_unit` (e.g., `Mouse,5,25.50`). Basic error handling addresses malformed files, but advanced validation is not implemented.

## Limitations and Future Work

The current implementation has some constraints that could be improved:

1. **Data Persistence**:
   - Data is lost on server restart due to in-memory storage. A database (e.g., SQLite, MySQL) could enable persistence.

2. **Scalability**:
   - In-memory storage limits handling of large datasets or concurrent users. A backend database or caching layer (e.g., Redis) could enhance scalability.

3. **Security**:
   - No authentication exists, making data publicly accessible. Adding user login and access controls would improve security.

4. **Error Handling**:
   - Only basic parsing error feedback is provided. More detailed validation (e.g., data type checks, file size limits) could improve usability.

5. **UI Enhancements**:
   - The dashboard lacks real-time updates or visualizations. Adding WebSockets for live updates or Chart.js for graphs could enrich the experience.

6. **Testing**:
   - No automated tests are included. Implementing JUnit for backend and Selenium for frontend testing would ensure reliability.

By addressing these areas, the application could become a more robust tool for sales data analysis.