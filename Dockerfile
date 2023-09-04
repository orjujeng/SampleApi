FROM openjdk:8-jre
EXPOSE 8080
VOLUME /tmp
ADD target/Timesheet-0.0.1-SNAPSHOT.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT [ "java","-jar","/app.jar"]