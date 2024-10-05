# Spring Boot Health Check and User Application

This is a simple Spring Boot application that provides a `/healthz` endpoint to check the connection to a MySQL database. The application is built using Maven and can be run as a standalone JAR file.

## Prerequisites

Before you begin, make sure you have the following installed on your system:

- [Java 21+](https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/what-is-corretto-21.html)
- [Maven 3.6+](https://maven.apache.org/install.html)
- [MySQL Database](https://dev.mysql.com/downloads/mysql/)

## Build the Application

To build the application, you need to compile the code and package it as a JAR file using Maven. Execute the following command in the root directory of the project:

```bash
mvn clean package
```

This will create a runnable JAR file in the target/ directory, for example:
```bash
target/healthz-assignment-0.0.1-SNAPSHOT.jar
```

## Run the Application
After building the application, you can run it using the java -jar command as follows:
```bash
java -jar target/healthz-assignment-0.0.1-SNAPSHOT.jar
```
## Externalizing the password
````bash
java -Dspring.datasource.password=putyourpassword -jar healthz-assignment-0.0.1-SNAPSHOT.jar
````
## /healthz Endpoint
The application provides a /healthz API endpoint that can be used to check the MySQL database connection.

Execute the /healthz API
To check the health of the database connection, you can send a GET request to:
```bash
http://localhost:8080/healthz
```

### If connection is good then below response will be returned.
```bash
curl -vvvv http://localhost:8080/healthz
*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
> GET /healthz HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.4.0
> Accept: */*
> 
< HTTP/1.1 200 
< Pragma: no-cache
< Cache-Control: no-store, no-cache, must-revalidate, max-age=0
< Content-Length: 0
< Date: Thu, 26 Sep 2024 16:45:04 GMT
< 
* Connection #0 to host localhost left intact  
```

###  If connection is not established then below response will be returned.
```bash
curl -vvvv http://localhost:8080/healthz
*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
> GET /healthz HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.4.0
> Accept: */*
> 
< HTTP/1.1 503 
< Pragma: no-cache
< Cache-Control: no-store, no-cache, must-revalidate, max-age=0
< Content-Length: 0
< Date: Thu, 26 Sep 2024 16:47:07 GMT
< Connection: close
< 
* Closing connection

```

###  for Not allowed methods
```bash
curl -vvvv -XPUT http://localhost:8080/healthz 
*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
> PUT /healthz HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.4.0
> Accept: */*
> 
< HTTP/1.1 405 
< Pragma: no-cache
< Cache-Control: no-store, no-cache, must-revalidate, max-age=0
< Content-Length: 0
< Date: Thu, 26 Sep 2024 16:49:01 GMT
< 
* Connection #0 to host localhost left intact

```

### Assignment 2
All the above commands can be used to build the project.

In this assignment we have taken care of below APIs.
1) /v1/user
2) /v1/user/self

Below environment variable can be passed to externalize the DB configuration.
#### DB_URL = The URL on which DB needs to connect.
#### DB_USERNAME = Username of DB.
#### DB_PASSWORD = Password of the DB.

Using the /v1/user command, user can be created in the application.

```bash
curl --location 'http://localhost:8080/v1/user' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic bmF2ZWVua2FyaGFuYTI5QGdtYWlsLmNvbTpJbmRpYW4xMjM0' \
--data-raw '{
    "first_name": "Dummy",
    "last_name": "user",
    "password":"Indian1234",
    "email":"dimmyuser@gamil.com"
}'
```

Using the /v1/user/self command user details can be fetched.
Be sure to pass the Authorization header with Basic token.

```bash
curl --location 'http://localhost:8080/v1/user/self' \
--header 'Authorization: Basic bmF2ZWVua2FyaGFuYTI5QGdtYWlsLmNvbTpJbmRpYW4xMjM0' 
```
Post /vi/user API wil return 201 status code.
