FROM openjdk:21
LABEL authors="Juanem"
VOLUME /tmp
ADD ./target/payment-0.0.1-SNAPSHOT.jar payment.jar
ENTRYPOINT ["java", "-jar", "/payment.jar"]