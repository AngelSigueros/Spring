FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
COPY target/spring-006-mysql-0.0.1-SNAPSHOT.jar App.jar
ENTRYPOINT ["java","-jar","/App.jar"]
EXPOSE 8080