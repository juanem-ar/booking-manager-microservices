FROM openjdk:21
LABEL authors="Juanem"
VOLUME /tmp

EXPOSE 8080

ADD ./target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

ENTRYPOINT ["java", "-jar", "/api-gateway.jar"]