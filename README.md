# Dynamic PDF Generator

This project is a Java Spring Boot application for generating and managing PDF files. It provides a REST API to accept data, generate a PDF based on the received data, allow the download of the generated PDF, and store the PDF on local storage to avoid regenerating it for the same data.

## Technologies Used
- Java
- Spring Boot
- Thymeleaf
- Flying Saucer PDF renderer


## Project Structure
The project is organized into the following main components:

- `src/main/java/com/info/DynamicPdfGenerator`: Contains the Java source files.
  - `controller`: Contains the REST controllers.
  - `service`: Contains the PDF generation service.
  - `model`: Contains the data models used in the application.
- `src/main/resources`: Contains the application properties and Thymeleaf templates.


## API Endpoints
- `POST /pdf/generate`: Generates a PDF based on the provided data.

## Configuration
- The PDFs are stored in the `pdf/directory` folder.
- To change the storage location, modify the `pdf.directory` value in the `application.properties` file.

## Testing

The application can be tested using Postman. Import the provided Postman collection and run the requests to test the endpoints.

## Contributors

- [NehaV](https://github.com/NV2480)
