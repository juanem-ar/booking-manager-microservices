FROM amazoncorretto:21-alpine-jdk
LABEL authors="Juanem"



ENTRYPOINT ["top", "-b"]