FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ADD build/libs/pre-order-app.jar pre-order-app.jar
ENTRYPOINT ["java", "-jar", "/pre-order-app.jar"]