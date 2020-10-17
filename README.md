# Subject Voting Service

This service intends to provide a way for Sicredi associates voting on subjects thru REST API based solution. 

### Application Documentation

* [Swagger](http://localhost:8081/api/voting-service/swagger-ui.html) [Only when application is running]
* [Technical Decisions](tech_decisions.md) [pt-BR]
* [Service Description and Improvements](features_desc.md) [pt-BR]

### Application Requirements

In order to run this application, make sure the following dependencies are configured in your local environment:

* [Java Development Toolkit 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
* [MongoDB](https://www.mongodb.com/download-center#community)
* [Kafka](http://kafka.apache.org/downloads.html)
* [ZooKeeper](https://zookeeper.apache.org/releases.html)

In case of running the application from IDE, do not forget to enable annotation processing for lombok.

##### Important: 

* It's mandatory to have MongoDB running at default port 27017 in your local environment.
* It's also desirable to have kafka running in your local environment. If you don't, please set `enabled` property to `false` at [application-local.yml](https://github.com/mourabd/votingservice/blob/main/votingservice/src/main/resources/application-local.yml)

```yaml
spring:
  kafka:
    bootstrapAddress: localhost:9092
    topic: votingEventTopic
    enabled: false
userInfoService:
  url: https://user-info.herokuapp.com/users
```

### Running the application (Windows OS)

Clone github repository:

```
git clone https://github.com/mourabd/voting-service.git
```

Run the following command from root application folder: 
```
gradlew build bootRun
```

The application should be running at port [8081](http://localhost:8081/).

### Starting ZooKeeper

Run the following command from installed zookeeper root folder: 
```
zkserver
```

### Starting Kafka

Run the following command from installed kafka root folder: 
```
.\bin\windows\kafka-server-start.bat .\config\server.properties
```

Run the following command from [installed-kafka-version]\bin\windows folder if you want to see kafka published events:

```
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic votingEventTopic
```

### Application Libraries Documentation

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/gradle-plugin/reference/html/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-kafka)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#production-ready)