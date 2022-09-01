# Ignitis Homework
REST API Spring Boot Project

### Requirements
* Java 11+
* Gradle 7.5

### Access the application
http://localhost:8080

## Run application
Application has several profiles:

`prod` - PostgreSQL will be used. Database is initialized with predefined admin user. There are supported two roles: Admin and User. See [application.yml](https://github.com/kristupasjovaisa/Ignitis.Homework/blob/dev/Homework/src/main/resources/application.yml) for configuration and credentials.

`dev` - Developement environment that uses [h2 in-memory database](http://localhost:8080/h2-console). See [application.yml](https://github.com/kristupasjovaisa/Ignitis.Homework/blob/dev/Homework/src/main/resources/application.yml) for configuration and credentials.

### Access Swagger API
* http://localhost:8080/swagger-ui/index.html