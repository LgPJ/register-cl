FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app

COPY target/register-cl.war app.war

CMD ["java", "-jar", "app.war"]
