FROM openjdk:21

WORKDIR /app

COPY . /app

RUN ./mvnw -B clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/apiBibliotecaDigital-0.0.1-SNAPSHOT.jar"]