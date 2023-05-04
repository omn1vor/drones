FROM amazoncorretto:17-alpine3.14
EXPOSE 8080
COPY ./build/libs/drones-0.0.1-SNAPSHOT.jar /usr/app/app.jar
WORKDIR /usr/app
CMD ["java", "-jar", "app.jar"]