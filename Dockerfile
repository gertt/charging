FROM openjdk:8

ADD . /home
WORKDIR /home
ADD target/has-to-be.jar home/has-to-be.jar

EXPOSE 8484
ENTRYPOINT ["java", "-jar", "home/has-to-be.jar"]

