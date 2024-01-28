FROM openjdk:11-jdk-slim as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

#RUN ./mvnw install -DskipTests
RUN --mount=type=cache,target=/root/.m2 ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:11-jdk-slim
VOLUME /tmp
COPY share /app/share
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","ru.otus.gorenkov.Task13Application"]


#FROM openjdk:11-jdk-slim
#WORKDIR /app
#COPY target/task-14-0.0.1-SNAPSHOT.jar task-14-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java","-jar","task-14-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar task-14-0.0.1-SNAPSHOT.jar"]


#FROM openjdk:11-jdk-slim
#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#ENTRYPOINT ["java","-cp","app:app/lib/*","ru.otus.gorenkov.Task13Application"]


#FROM openjdk:11-jdk-slim
#WORKDIR /app
#ARG EXTRACTED=target/extracted
#COPY ${EXTRACTED}/dependencies/ ./
#COPY ${EXTRACTED}/spring-boot-loader/ ./
#COPY ${EXTRACTED}/snapshot-dependencies/ ./
#COPY ${EXTRACTED}/application/ ./
#ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]
