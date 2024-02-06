FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
WORKDIR /app
COPY run.sh .
COPY target/*.jar app.jar
ENTRYPOINT ["./run.sh"]