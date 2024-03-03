FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ADD ./pre-purchase.jar pre-purchase.jar
ENTRYPOINT ["java", "-jar", "/pre-purchase.jar"]