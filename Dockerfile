FROM openjdk:14-alpine
COPY build/libs/shipping-svc-*-all.jar shipping-svc.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "shipping-svc.jar"]