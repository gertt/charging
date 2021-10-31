#CHARGING PROCESS
Charging process rating is a process of applying a rate to a CDR for establishing a price for a charging process. 

#Charging Process
Has two imppotant events that are sent by charging station to CSMS:
Start Transaction
Stop Transaction

#CDR
Is a combination of these values: meterStart, timestampStart, meterStop and timestampStop.

#Charging process rating
Rating is the process that applies a rate to a CDR.
Rate can have these components:
energy - rate the charging process based on enery consumed.
time - rate the charging process based on its duration.
transaction - fees per charging process.

#The purpose of the project
This project will apply(calculate) a rate to a corresponding CDR.

#Build with
Spring Boot version 2.5.4
Java 1.8
Lambok 1.18.20
Javax validation 2.0.1.FINAL
Slf4j 1.7.30

#Run the project
Be sure to not have in use the port number 7070 otherwise you have to change it.
Executing the following endpoint in Postman, the user can get the response of calculated rate:
http://localhost:7070/charging-process/rate

#Implementation
Creating  a RESTful API (POST) with a json input :
{
    "rate": {
        "energy": 0.3,
        "time": 2,
        "transaction": 1
    },
    "cdr": {
        "meterStart": 1204307,
        "timestampStart": "2021-04-05T10:04:00Z",
        "meterStop": 1215230,
        "timestampStop": "2021-04-05T11:27:00Z"
    }
}

The output when API returns success will be the following:
Response 200 :
{
    "success": true,
    "message": "Rate output !",
    "data": {
        "overall": 7.044,
        "components": {
            "energy": 3.277,
            "time": 2.767,
            "transaction": 1
        }
    }
}

The output when API returns error will be the following:
Request : energy is null
{
    "rate": {
        "energy": null,
        "time": 2,
        "transaction": 1
    },
    "cdr": {
        "meterStart": -1204307,
        "timestampStart": "2021-04-05T10:04:00Z",
        "meterStop": 1215230,
        "timestampStop": "2021-04-05T11:27:00Z"
    }
}

Response 400 : 
{
    "success": false,
    "message": "Bed Request",
    "fieldErrors": [
        "energy field is mandatory"
    ]
}

Response 500 :
{
    "success": false,
    "message": "Internal Server Error"
}

#Tests
Testing is an important step of the development process to create a high-quality application.
It determines that the app can be successfully executed and downloaded.

#WRITE CODE TO TEST THE CODE
To make sure that our project is functional, are generated automated test cases for each layer.
First layer is controller class and than the service layer.
Every test is generated in its own directory.
See the example test in src/test/java for an example of a component test.
Adding test cases for validation of fields in request and response
Validation cases:
Check if field is null or if has negative value or empty.

#Running the tests
Tests can be run one by one or all together in the whole project.
One by One - running one single test.
All tests in a test class- running all tests of class in one time.
All tests in the whole project- running all tests of the project together.

Copyright © 2021, Gerti Prifti
