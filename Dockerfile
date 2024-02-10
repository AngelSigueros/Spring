FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
COPY target/*.jar App.jar
ENTRYPOINT ["java","-jar","/App.jar"]
EXPOSE 8080