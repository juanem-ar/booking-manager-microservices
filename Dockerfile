FROM amazoncorretto:21-alpine-jdk
LABEL authors="Juanem"

COPY discovery-server/target/discovery-server-0.0.1-SNAPSHOT.jar discovery.jar
COPY api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar gateway.jar
COPY business-unit/target/business-unit-0.0.1-SNAPSHOT.jar bu.jar
COPY booking/target/booking-0.0.1-SNAPSHOT.jar book.jar
COPY availability/target/availability-0.0.1-SNAPSHOT.jar ava.jar
COPY payment/target/payment-0.0.1-SNAPSHOT.jar pay.jar
COPY rate/target/rate-0.0.1-SNAPSHOT.jar rate.jar


ENTRYPOINT ["java", "-jar", "/discovery.jar", "/gateway.jar","/bu.jar", "/book.jar", "/ava.jar", "/pay.jar", "/rate.jar"]