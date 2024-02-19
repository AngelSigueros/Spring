FROM eclipse-temurin:21-jdk-jammy
#
## Establece el directorio de trabajo en la imagen
WORKDIR /app1
#
COPY . /app1
###RUN mvn clean clean
EXPOSE 8080
CMD ["java", "-jar", "target/spring-007-repository-0.0.1-SNAPSHOT.jar"]