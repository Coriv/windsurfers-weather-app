# Windsurfing Weather App

This Java 17 application allows you to search for the best windsurfing location based on a given date. 
It also provides the ability to edit the list of locations to be checked. 
The app utilizes the weatherbit.io external API to fetch weather data and is fully tested. 
It is built using Spring Boot 3, Spring Web, Lombok, and Java 17.
Configuration data is loaded from the `application.yml` file. 

Please note that the input date should be within the valid range of today to a maximum of 7 days from today, as per the restrictions of the API key.

## Compilation Process

To compile the project, follow these steps:

1. Clone this repository to your local machine:

       git clone https://github.com/Coriv/windsurfers-weather-app.git
2. Open a terminal and navigate to the project's root directory:
   
       cd windsurfers-weather-app
3. Run the following command to pull the latest changes from the repository:
   
       git pull
4. Run the following command to compile the project:

       gradle build
5. Once the build process is complete, you can run the application using the following command:
   
       gradle bootRun

This will start the application, and it will be accessible at `http://localhost:8080`.

Remember to update the necessary configuration properties in the `application.yml` file before running the application, such as API keys or database connection details.

## Testing

You can run the tests using the following command:

    gradle test

This command will execute all the unit tests and generate a test report.



## Future Enhancements

In the future, the following enhancements are planned for the Windsurfing Weather App:

- Integration of MySQL as an external persistence source: This will allow storing and retrieving location data from a database, providing more flexibility and scalability.

- Integration of Swagger: Adding Swagger documentation will make the application's endpoints more visible and accessible, allowing for easier API exploration and testing.

Enjoy using the Windsurfing Weather App!


## API Endpoints

### GET /api/v1/best-location/{date}

Endpoint to fetch the best windsurfing location for a given date.

**Path Parameters:**
- `date` (type: LocalDate) - The date for which to find the best location.

**Responses:**
- `200 OK` - Successful request. Returns a `LocationWeatherDto` object containing details of the best windsurfing location.
- `404 Not Found` - If weather details for the given date do not exist.

### GET /api/v1/locations

Endpoint to fetch the list of checked locations.

**Responses:**
- `200 OK` - Successful request. Returns a list of strings containing the names of checked locations.

### POST /api/v1/add-location

Endpoint to add a new location to the list of checked locations.

**Query Parameters:**
- `city` (type: String) - The city name to add to the list of checked locations.

**Responses:**
- `200 OK` - Successful request. The new location has been added to the list.

### DELETE /api/v1/remove-location

Endpoint to remove a location from the list of checked locations.

**Query Parameters:**
- `city` (type: String) - The city name to remove from the list of checked locations.

**Responses:**
- `200 OK` - Successful request. The location has been removed from the list.




