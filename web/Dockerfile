FROM openjdk:17-jdk-alpine
MAINTAINER kcybulski.me
VOLUME /tmp
EXPOSE 8080
ADD build/libs/web-0.0.1-SNAPSHOT.jar web.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/web.jar"]

