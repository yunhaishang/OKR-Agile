# Docker 配置指南

本项目使用 Docker 和 Docker Compose 来简化开发和部署过程，同时支持不同开发者使用不同的数据库配置。

## 文件说明

- `.env.example`: 环境变量模板文件，包含所有可配置的环境变量
- `.env`: 实际的环境变量文件，包含敏感信息（不会被提交到版本控制）
- `Dockerfile`: 用于构建应用镜像的配置文件
- `docker-compose.yml`: 用于编排应用和数据库容器的配置文件

## 使用指南

### 首次设置

1. 复制环境变量模板文件：
   ```
   cp .env.example .env
   ```

2. 编辑 `.env` 文件，设置你的数据库配置：
   ```
   # 数据库配置
   DB_HOST=localhost
   DB_PORT=3306
   DB_NAME=okr_agile_db
   DB_USERNAME=your_username
   DB_PASSWORD=your_password

   # 应用配置
   APP_PORT=8080
   ```

### 运行项目

1. 确保已安装 Docker 和 Docker Compose

2. 启动所有服务：
   ```
   docker-compose up -d
   ```

   这将构建并启动应用和数据库容器。

3. 查看日志：
   ```
   docker-compose logs -f
   ```

4. 停止服务：
   ```
   docker-compose down
   ```

### 开发流程

- 每个开发者都应该有自己的 `.env` 文件，其中包含自己的数据库配置
- `.env` 文件已经在 `.gitignore` 中，不会被提交到版本控制
- 当需要修改配置时，可以更新 `.env.example` 文件，然后通知其他团队成员同步更新

## 注意事项

- 数据库数据会保存在 Docker 卷中，即使容器重启也不会丢失
- 如需重置数据库，可以删除 Docker 卷：
  ```
  docker-compose down -v
  ```
