FROM --platform=linux/amd64 eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


#
#01. Run mvn clean package command
#02. docker build -t hiransanjeewa/tictactoe .
#03.  docker run -p 8080:8080 hiransanjeewa/tictactoe

