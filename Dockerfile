FROM openjdk:8
EXPOSE 8087
ADD target/gestion-station-ski-1.0.jar skitest.jar
ENTRYPOINT ["java", "-jar", "/skitest.jar"]