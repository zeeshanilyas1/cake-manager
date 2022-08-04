FROM openjdk:17
EXPOSE 8282
ADD target/cake-manager-docker.jar cake-manager-docker.jar
ENTRYPOINT ["java", "jar", "/cake-manager-docker.jar"]