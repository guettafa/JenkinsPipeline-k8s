FROM eclipse-temurin:21
ARG JAR_FILE=/target/*.jar

ARG SPRING_PROFILE
# En ENV var sinon valeur se perd
ENV PROFILE=${SPRING_PROFILE}

COPY ${JAR_FILE} app.jar
ENTRYPOINT java -jar /app.jar --spring.profiles.active=${PROFILE}