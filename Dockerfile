FROM debian:bookworm

RUN apt-get update && apt-get install -y \
 openjdk-21-jdk \
 maven \
 && rm -rf /var/lib/apt/lists/


 COPY ./app /home/app
 WORKDIR /home/app

 EXPOSE 8080

 ENTRYPOINT ["mvn", "spring-boot:run"]


