# 1단계: 빌드 단계
FROM openjdk:17-slim AS builder

WORKDIR /build

# 소스 코드 및 Gradle Wrapper 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew

# Gradle 빌드 실행 (JAR 파일 생성)
RUN ./gradlew clean bootJar --info

# 2단계: 실행 단계
FROM openjdk:17-slim

WORKDIR /app

# 빌드된 JAR 파일을 실행 이미지에 복사
COPY --from=builder /build/build/libs/*.jar /app/app.jar

# 컨테이너가 실행할 명령
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# 애플리케이션 포트
EXPOSE 9090
