FROM eclipse-temurin:21-jdk-jammy
VOLUME /tmp
COPY target/spring-006-mysql-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080