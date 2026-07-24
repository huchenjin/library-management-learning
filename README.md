# 图书管理后台学习项目

这是一个用于熟悉 Java 21、Spring Boot 4、Maven、MyBatis-Plus 和 Vue 3 开发流程的前后端分离项目。脚手架已经完成通用基础设施、JWT 登录和图书查询示例，其余业务被拆成循序渐进的学习任务。

## 技术栈

- 后端：Java 21、Spring Boot 4.1、Maven Wrapper、Spring Security、MyBatis-Plus 3.5.17、Flyway、MySQL 8.4、OpenAPI。
- 前端：Vue 3、TypeScript、Vite、Element Plus、Pinia、Vue Router、Axios。
- 测试：JUnit 5、Mockito、Testcontainers、Vue TypeScript 检查、ESLint。

## 目录

```text
.
├── compose.yaml              # 本地 MySQL
├── docs/requirements.md      # 需求、规则和学习任务
├── library-server            # Spring Boot 后端
└── library-web               # Vue 管理后台
```

## 环境准备

当前项目使用 Java 21。本机不需要单独安装 Maven，因为 `library-server` 已带 Maven Wrapper。

macOS 可安装 Temurin 21：

```bash
brew install --cask temurin@21
java -version
```

还需要 Docker Desktop，以及 Node.js 22 或更高版本。

## 启动项目

1. 启动 MySQL：

```bash
docker compose up -d mysql
docker compose ps
```

2. 启动后端：

```bash
cd library-server
./mvnw spring-boot:run
```

3. 启动前端：

```bash
cd library-web
npm install
npm run dev
```

浏览器打开 `http://localhost:5173`，开发账号为：

```text
用户名：admin
密码：admin123
```

Swagger 地址：`http://localhost:8080/swagger-ui.html`。

## 常用命令

```bash
# 后端测试
cd library-server && ./mvnw test

# 前端质量检查
cd library-web
npm run type-check
npm run lint
npm run build
```

后端的 Testcontainers 测试会使用 Docker 自动启动临时 MySQL。学习任务测试默认带有 `@Disabled`，完成某个任务后再启用对应测试。

## 配置说明

默认配置适合本地学习。可以复制 `.env.example` 为 `.env` 调整 Compose 配置，并通过环境变量覆盖后端连接信息：

- `DB_URL`、`DB_USERNAME`、`DB_PASSWORD`
- `JWT_SECRET`：至少 32 字节，非本地环境必须修改
- `SERVER_PORT`

## 推荐学习方式

1. 先运行项目，登录并使用图书列表筛选和详情。
2. 阅读 `BookController`、`BookService`、`BookMapper.xml`，理解完整请求链路。
3. 打开 [需求文档](docs/requirements.md)，从 `LEARNING-1` 开始。
4. 全局搜索 `TODO(LEARNING-` 找到练习入口。
5. 每完成一项，启用对应验收测试并补齐前端页面。

> 默认账号和密码仅用于本地学习，不能用于生产环境。
