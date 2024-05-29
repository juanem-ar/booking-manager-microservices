FROM openjdk:21
LABEL authors="Juanem"
VOLUME /tmp
ADD ./target/availability-0.0.1-SNAPSHOT.jar availability.jar
ENTRYPOINT ["java", "-jar", "/availability.jar"]