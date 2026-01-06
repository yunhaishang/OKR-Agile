# 使用官方的OpenJDK 17作为基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制Maven配置文件
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# 下载依赖（利用Docker缓存层）
RUN ./mvnw dependency:go-offline -B

# 复制源代码
COPY src ./src

# 构建应用
RUN ./mvnw clean package -DskipTests

# 暴露应用端口
EXPOSE ${APP_PORT:-8080}

# 启动应用
ENTRYPOINT ["java", "-jar", "target/okr-agile-0.0.1-SNAPSHOT.jar"]
