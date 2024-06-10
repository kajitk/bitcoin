# Bitcoin API

This project is a Spring Boot application that provides an API to fetch historical Bitcoin prices. It includes the highest and lowest price markers within a specified date range and displays these prices in a basic HTML UI. The service supports offline mode and is designed for easy deployment to a production environment.

## Features

- Fetch historical Bitcoin prices for a given date range.
- Identify the highest and lowest prices within the date range.
- Convert prices to a specified output currency.
- Support for offline mode with toggle.
- Swagger documentation for API.

## API Endpoints

### Get Historical Prices

**URL**: `/api/bitcoin/historical-prices`

**Method**: `GET`

**Parameters**:
- `startDate` (String, required): Start date in DD-MM-YYYY format.
- `endDate` (String, required): End date in DD-MM-YYYY format.
- `outputCurrency` (String, required): Output currency (e.g., USD, EUR).

**Response**:
- `200 OK`: Returns the historical prices, highest price and lowest price in the specified currency.
- `500 Internal Server Error`: If there is an error fetching the historical prices.

### Toggle Offline Mode

**URL**: `/api/bitcoin/toggle-offline`

**Method**: `POST`

**Parameters**:
- `offline` (boolean, required): Enable or disable offline mode.

**Response**:
- `200 OK`: Offline mode toggled successfully.

## Setup and Running

1. Clone the repository:
    ```sh
    git clone https://github.com/kajitk/bitcoin.git
    ```

2. Navigate to the project directory:
    ```sh
    cd bitcoin
    ```

3. Build the project using Maven:
    ```sh
    mvn clean install
    ```

4. Run the application:
    ```sh
    mvn spring-boot:run
    ```

5. Access the application:
    - HTML UI: http://localhost:8080
    - Swagger UI: http://localhost:8080/swagger-ui/index.html

## Docker Deployment

1. Build the Docker image:
    ```sh
    docker build -t bitcoin .
    ```

2. Run the Docker container:
    ```sh
    docker run -d -p 8080:8080 --name bitcoin-price bitcoin
    ```

3. Access the application:
    - HTML UI: http://localhost:8080
    - Swagger UI: http://localhost:8080/swagger-ui/index.html

4. Alternatively, you can use Docker Compose for local deployment:
    ```sh
    docker-compose up
    ```

## Jenkins CI/CD Pipeline

This project includes a Jenkins pipeline script to automate the build, test, and deployment process. The pipeline script is defined in the `Jenkinsfile`.

### Jenkins Pipeline Steps

1. **Build**: Clean and build the Maven project.
2. **Build Docker Image**: Build the Docker image using the Dockerfile.
3. **Run Tests**: Run the unit tests using Maven.
4. **Deploy**: Deploy the Docker container.

## Design and Implementation

The application is designed with a RESTful architecture, providing a clear separation between the API endpoints and the data processing logic. The controller handles incoming requests, processes the data, and returns the appropriate response.

### Sequence Diagram

![Sequence Diagram](sequence-diagram.png)

### Design Patterns Used

- **Singleton Pattern**: Ensuring a single instance of configuration settings (e.g., offline mode flag).
- **Builder Pattern**: Used in the Swagger configuration for building the API documentation.

## Technologies Used

- Spring Boot
- Swagger
- Maven
- Docker
- Jenkins
- HTML for the UI