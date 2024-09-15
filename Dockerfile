FROM openjdk:21
COPY /target/dock_demo-0.0.1-SNAPSHOT.jar /tmp/appl.jar
WORKDIR /tmp
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "appl.jar"]
