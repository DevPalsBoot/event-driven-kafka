# 베이스 이미지 설정
FROM openjdk:21-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 소스 코드 복사 및 Gradle 빌드
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

# JAR 파일 복사
COPY ./build/libs/report.jar /app/report.jar

# JAR 파일 권한 수정
RUN chmod 755 /app/report.jar

# 파일 존재 여부 확인
RUN ls -l /app/report.jar

# 컨테이너가 실행할 명령
ENTRYPOINT ["java", "-jar", "/app/report.jar"]

# 9090 포트로 애플리케이션이 실행됨
EXPOSE 9090
