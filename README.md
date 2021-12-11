# KryStatusMonitorProject

Database Used :- MongoDB

Build :- Maven

Steps to run the project :- 
1. Port for the application = 9090
2. To run the project either import project in your preffered IDE or run following commands :-
    
    mvn install / mvn clean install
    
    mvn spring-boot:run 
    
REST Endpoints for testing :- 

"id" is integer value and "name" is string value.

1. GET http://localhost:9090/statusMonitorService/{{id}} :- "id" is user id for which you need to see the status of all the added services. 
Response e.g. :- 
```
[
    {
        "url": "http://localhost:8080",
        "name": "service1",
        "creationTime": "2021-12-11T12:33:40.652Z",
        "healthList": [
            {
                "status": "UP",
                "time": "2021-12-11T11:52:47.488Z"
            },
            {
                "status": "UP",
                "time": "2021-12-11T12:28:09.345Z"
            }
        ]
    },
    {
        "url": "http://localhost:8081",
        "name": "service2",
        "creationTime": "2021-12-11T12:33:40.664Z",
        "healthList": [
            {
                "status": "DOWN",
                "time": "2021-12-11T11:52:53.981Z"
            },
            {
                "status": "DOWN",
                "time": "2021-12-11T12:28:09.347Z"
            }
        ]
    }
]
 ```
 
2. POST http://localhost:9090/statusMonitorService/{{id}}/addurl :- "id" is the user id for which you need to add a service for monitoring. 
   e.g. body:- 
   ```
   {
        "url": "http://localhost:8080",
        "name": "service1"
   }
   ```
  
3. GET http://localhost:9090/statusMonitorService/{{id}}/{{name}} :- "id" is user id and "name" is the service name for which you need to check the status. 
Response e.g. :- 
 ```
   {
        "url": "http://localhost:8080",
        "name": "service1",
        "creationTime": "2021-12-11T12:33:40.652Z",
        "healthList": [
            {
                "status": "UP",
                "time": "2021-12-11T11:52:47.488Z"
            },
            {
                "status": "UP",
                "time": "2021-12-11T12:28:09.345Z"
            }
        ]
    }
 ```
4. DELETE http://localhost:9090/statusMonitorService/{{id}}/{{name}} :-  "id" is user id and "name" is the service name which you want to delete for the provided user id.

5. The application updates the status for all the services added by all the users for every 5000 milliseconds. This value can be updated in StatusMonitorScheduler.java.
