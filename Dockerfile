FROM maven:3.6.1-jdk-13
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
# RUN mvn -f /usr/src/app/pom.xml clean package

# FROM openjdk:13-jdk-alpine
# VOLUME /tmp
# COPY --from=build /usr/src/app/target//url-shortener-0.0.1-SNAPSHOT.jar app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]