FROM openjdk:21
LABEL authors="Juanem"
VOLUME /tmp
ADD ./target/booking-0.0.1-SNAPSHOT.jar booking.jar
ENTRYPOINT ["java", "-jar", "/booking.jar"]