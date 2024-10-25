# 개발 가이드 📋
> 해당 프로젝트는 스프링 부트 환경에서 Kafka를 활용해 비동기 프로그래밍을 구현한 애플리케이션입니다.    
> 아래 개발 가이드에서는 프로젝트 설정, Docker Compose를 통한 환경 구축 방법, 테스트 방법을 안내합니다.
- [프로젝트 설정](#1-프로젝트-설정)
    - [환경 변수 설정](#11-환경-변수-설정)
    - [환경 변수 설명](#12-환경-변수-설명)
- [개발 환경 세팅](#2-개발-환경-세팅)
    - [Docker Plugin 설치](#21-docker-plugin-설치)
    - [Docker 설정](#22-docker-설정)
    - [Run Configuration 추가](#23-run-configuration-추가)
- [테스트 방법](#3-테스트-방법)


## 1. 프로젝트 설정 ⚙️

---

### 1.1 환경 변수 설정
#### `backend/src/main/resources/application.yml`
```yaml
todo

```
#### `report/src/main/resources/application.yml`
```yaml
spring:
  kafka:
    bootstrap-servers: ${SPRING_KAFKA_BOOTSTRAP_SERVERS:localhost:9092}
    consumer:
      group-id: my-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer

```
### 1.2 환경 변수 설명
- **`bootstrap-servers`**: Kafka 서버 주소. Spring Boot에서 Kafka와 통신할 수 있도록 합니다.
- **`consumer.group-id`**: 같은 Consumer 그룹에 속하는 모든 Consumer는 동일한 메시지를 수신하지 않도록 조정됩니다.
- **`auto-offset-reset`**: 오프셋이 없을 때 메시지를 어디서부터 읽어올지 결정합니다. `earliest`, `latest`가 대표적인 옵션입니다.
- **`producer.key-serializer / value-serializer`**: Producer가 메시지를 Kafka에 보낼 때 키와 값을 직렬화하는 방식입니다. 기본적으로 `StringSerializer`를 사용합니다.
- **`consumer.key-deserializer / value-deserializer`**: Consumer가 Kafka에서 메시지를 수신할 때 키와 값을 역직렬화하는 방식입니다. 기본적으로 `StringDeserializer`를 사용합니다.

## 2. 개발 환경 세팅 🛠️

---

> 이 프로젝트는 `docker-compose`를 사용하여 필요한 개발 환경을 구성할 수 있습니다.  
IntelliJ에서 `docker-compose`로 실행 환경을 구성하는 방법은 다음과 같습니다.


### 2.1 Docker Plugin 설치 
`File > Settings > Plugins`에서 Docker 플러그인 설치
### 2.2 Docker 설정 
`File > Settings > Build, Execution, Deployment`에서 설치한 Docker 추가
### 2.3 Run Configuration 추가 
`Run/Debug Configuration`에서 Docker 환경 추가 후 `Compose Files`에 `./docker-compose.yml` 설정

## 3. 테스트 방법 ✅
todo