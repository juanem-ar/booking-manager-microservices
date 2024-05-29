FROM openjdk:21
LABEL authors="Juanem"
VOLUME /tmp

EXPOSE 8888

ADD ./target/server-config-0.0.1-SNAPSHOT.jar config-server.jar

ENTRYPOINT ["java", "-jar", "/config-server.jar"]