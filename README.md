This is a Back end application for Foodordering System. This is developed using SpringBoot, Java & JEE.

Prerequisites:
Maven, Postgres DB, Java 8 above
The postgres DB configuration esxists on FoodOrderingAppBackend/FoodOrderingApp-api/src/main/resources/application.yaml and FoodOrderingAppBackend/FoodOrderingApp-db/src/main/resources/config/localhost.properties.

Build:
To build this and run unit-tests, use the command "mvn clean install".
To build the application and run the DB consifuration with test data "mvn clean install -Psetup"
To view the Swagger UI for the same, on browser url localhost:8080/api/swagger-ui.html

Note:
There is a separate Frontend application supporting this backend REST. The details of which will be added laater.
