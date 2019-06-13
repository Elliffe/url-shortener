FROM openjdk:13-jdk-alpine
MAINTAINER robbienolan@me.com

VOLUME /tmp
COPY target/url-shortener-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]