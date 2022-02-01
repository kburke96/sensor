# sensor
Spring Boot REST API for Sensor data

## Features 
* Spring Boot 2.6.2
* Java 11
* Maven 
* HSQL In-memory database
* Lombok

## Pre-requisites
This project has been tested using:
* Linux Mint 20
* VS Code
* openjdk version 11.0.9

## Installation

To run this project:
1. Clone the repository from GitHub
```bash
git clone https://github.com/kburke96/sensor.git
```

2. Go to the root directory and run as s Spring Boot project with Maven:
```bash
cd sensor
./mvnw spring-boot:run
```

> **Note:** This application runs on the standard port 8080.



## Using the application

Once the application is running, use Postman or another HTTP client to interact with it.

All URLs are rooted with /sensor

The following methods are available to work with the API:

**1. Add a new sensor to the database**
##### Example
```bash
locahost:8080/sensors/add
````

##### Result
If the call is successful, the application sends back the new Sensor object that was added, in JSON format.
```json
{
    "id": 3,
    "countryName": "Ireland",
    "cityName": "Limerick",
    "temperatures": [
        23,34,12,10
    ]
}
```

**2. Query all sensors in the database**
##### Example
```bash
localhost:8080/sensors/all
```
##### Result
The application sends back a list of all sensors in the database, in JSON format.
```json
[
    {
        "id": 1,
        "countryName": "Ireland",
        "cityName": "Limerick",
        "temperatures": [
            23,
            34,
            12,
            10
        ]
    },
    {
        "id": 2,
        "countryName": "Ireland",
        "cityName": "Galway",
        "temperatures": [
            2,
            3,
            1,
            10
        ]
    }
]
```

**3. Get average temperature for a given time range**

This method takes one PathVariable and one RequestParam.
The PathVariable is the ID of the sensor to be queried.
The RequestParam is the number of previous days to find the average for (default is 1).
##### Example
```bash
localhost:8080/sensors/1/gettemp?days=3
```
##### Result
The application returns a ResponseEntity containing a single integer value, this is the average temperature.
```json
18
```

**4. Add a temperature to a sensor**

This method takes one PathVariable and one RequestParam.
The PathVariable is the ID of the sensor to be queried.
The RequestParam is the temperature to be added to the sensor.
##### Example
```bash
localhost:8080/sensors/1/addtemp?temp=14
```

##### Result
The application returns the new Sensor object with the updated list of temperatures, in JSON format.
```json
{
    "id": 1,
    "countryName": "Ireland",
    "cityName": "Limerick",
    "temperatures": [
        23,
        34,
        12,
        10,
        14
    ]
}
```

## Known Issues and Limitations
* **Security**: This API does not implement any of the features provided by the Spring Security project. It is always best practice to implement some form of security (basic auth, JWT, etc.) on any API.
* **Data Storage**: This API stores the temperature data for each sensor as a List of integers. There is an assumption that this list is in chronological order. In a scaled-up, or multi-threaded environment, this may not be the best solution and could lead to concurrency issues or data corruption. 

## Future Ehancements
* This API only holds temperature data for each sensor. If further data needs to be stored, the Sensor model can easily be updated to hold it, and the existing patterns in the code can be modified to serve the new data.
* Custom exceptions should be added to make the API more user friendly. If required path varaibles or request parameters are omitted, the error message should flag these to the user.
* Logging should be added using standard logging frameworks (slf4j, log4j etc.)
* A frontend application could be developed on top of this API to allow users to visualise and input data easily.
* This API could be used as a microservice in a containerised environment. 