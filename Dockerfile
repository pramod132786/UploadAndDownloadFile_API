FROM openjdk:17
COPY target/UploadOrDownloadFiles_API-0.0.1-SNAPSHOT.jar/ usr/app/

WORKDIR /usr/app

EXPOSE 9003

ENTRYPOINT [ "java" ,"-jar","UploadOrDownloadFiles_API-0.0.1-SNAPSHOT.jar"]