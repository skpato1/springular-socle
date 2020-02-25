FROM anapsix/alpine-java:8
ADD springular-framework.jar /springular-framework.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=rec", "-jar", "/springular-framework.jar"]
