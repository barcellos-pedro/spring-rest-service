FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
WORKDIR /app
COPY run.sh .
COPY target/*.jar app.jar
RUN chmod +x run.sh
ENTRYPOINT ["./run.sh"]