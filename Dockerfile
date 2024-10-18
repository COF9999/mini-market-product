FROM amazoncorretto:21-alpine-jdk

COPY target/items-db-II-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

