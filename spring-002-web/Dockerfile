FROM eclipse-temurin:21-jdk-jammy
# Establece el directorio de trabajo en la imagen
WORKDIR /app
#
COPY . /app
COPY src ./src
##RUN mvn clean clean
CMD ["java", "-jar", "target/spring-002-web-0.0.1-SNAPSHOT.jar"]