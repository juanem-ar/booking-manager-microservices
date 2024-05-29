FROM openjdk:21
LABEL authors="Juanem"
VOLUME /tmp
ADD ./target/rate-0.0.1-SNAPSHOT.jar rate.jar
ENTRYPOINT ["java", "-jar", "/rate.jar"]