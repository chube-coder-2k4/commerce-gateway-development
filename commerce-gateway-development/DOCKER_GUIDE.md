# 🐳 HƯỚNG DẪN SỬ DỤNG DOCKER CHO NGƯỜI MỚI BẮT ĐẦU

## 📚 Mục lục
1. [Docker là gì?](#docker-là-gì)
2. [Cấu trúc Project](#cấu-trúc-project)
3. [Giải thích các khái niệm](#giải-thích-các-khái-niệm)
4. [Hướng dẫn sử dụng](#hướng-dẫn-sử-dụng)
5. [Troubleshooting](#troubleshooting)

---

## 🤔 Docker là gì?

Docker giúp bạn "đóng gói" ứng dụng cùng tất cả dependencies (database, cache, v.v.) vào các **container**. 

**Lợi ích:**
- ✅ Không cần cài PostgreSQL, Redis, RabbitMQ trên máy local
- ✅ Môi trường giống nhau trên mọi máy (Dev, Test, Production)
- ✅ Dễ dàng start/stop toàn bộ hệ thống bằng 1 lệnh
- ✅ Không lo conflict port hay version

---

## 📂 Cấu trúc Project

```
commerce-gateway-development/
├── Dockerfile              # Hướng dẫn build Docker image cho Spring Boot app
├── docker-compose.yml      # Định nghĩa tất cả services (PostgreSQL, Redis, App...)
├── .dockerignore          # File bỏ qua khi build Docker image
└── src/                   # Source code
```

---

## 💡 Giải thích các khái niệm

### 1. **Dockerfile**
- Là "công thức" để tạo Docker Image
- Ví dụ: Lấy Java 21, copy code, build JAR, chạy app

### 2. **Docker Image**
- Là "khuôn mẫu" đã build sẵn
- Ví dụ: `postgres:16-alpine` là image PostgreSQL

### 3. **Docker Container**
- Là "thể hiện đang chạy" của Image
- Ví dụ: Container `commerce-postgres` chạy từ image `postgres:16-alpine`

### 4. **docker-compose.yml**
- Định nghĩa nhiều services cùng lúc
- Services trong project này:
  ```
  ┌─────────────────────────────────────┐
  │    commerce-app (Spring Boot)       │
  │         Port: 8085                  │
  └──────────┬──────────────────────────┘
             │ Kết nối với:
             │
  ┌──────────▼──────────┐  ┌──────────────┐  ┌──────────────┐
  │   PostgreSQL        │  │    Redis     │  │  RabbitMQ    │
  │   Port: 5432        │  │  Port: 6379  │  │  Port: 5672  │
  └─────────────────────┘  └──────────────┘  └──────────────┘
  ```

### 5. **Networks**
- Mạng riêng để containers giao tiếp với nhau
- `commerce-network`: Tất cả containers trong cùng 1 mạng

### 6. **Volumes**
- Lưu trữ dữ liệu persistent (không mất khi tắt container)
- `postgres-data`: Lưu database
- `redis-data`: Lưu cache

### 7. **Environment Variables**
- Biến môi trường để config
- Ví dụ: `SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/commercegatewaydb`
- ⚠️ Chú ý: Dùng `postgres` (tên container) thay vì `localhost`

---

## 🚀 Hướng dẫn sử dụng

### Bước 1: Cài đặt Docker
- Windows: [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- Kiểm tra: `docker --version` và `docker-compose --version`

### Bước 2: Khởi động tất cả services

```bash
# Di chuyển vào thư mục project
cd D:\commerce-gateway-development\commerce-gateway-development

# Khởi động (build image nếu chưa có)
docker-compose up -d

# Xem logs
docker-compose logs -f

# Chỉ xem logs của Spring Boot app
docker-compose logs -f app
```

**Giải thích lệnh:**
- `up`: Khởi động services
- `-d`: Detached mode (chạy background)
- `-f`: Follow logs (xem realtime)

### Bước 3: Kiểm tra trạng thái

```bash
# Xem danh sách containers
docker-compose ps

# Kết quả mong đợi:
# NAME                 STATUS              PORTS
# commerce-app         Up 2 minutes        0.0.0.0:8085->8085/tcp
# commerce-postgres    Up 2 minutes        0.0.0.0:5432->5432/tcp
# commerce-redis       Up 2 minutes        0.0.0.0:6379->6379/tcp
# commerce-rabbitmq    Up 2 minutes        0.0.0.0:5672->5672/tcp, 0.0.0.0:15672->15672/tcp
```

### Bước 4: Truy cập ứng dụng

| Service | URL | Thông tin đăng nhập |
|---------|-----|---------------------|
| Spring Boot API | http://localhost:8085 | - |
| Swagger UI | http://localhost:8085/swagger-ui.html | - |
| RabbitMQ Management | http://localhost:15672 | admin / admin123 |
| PostgreSQL | localhost:5432 | postgres / 12345 |
| Redis | localhost:6379 | - |

### Bước 5: Dừng services

```bash
# Dừng (giữ lại data)
docker-compose down

# Dừng và XÓA DATA (cẩn thận!)
docker-compose down -v
```

---

## 🔧 Các lệnh thường dùng

### Rebuild khi thay đổi code

```bash
# Rebuild và restart
docker-compose up -d --build

# Chỉ rebuild service app
docker-compose up -d --build app
```

### Xem logs

```bash
# Tất cả logs
docker-compose logs

# Logs của 1 service
docker-compose logs app
docker-compose logs postgres

# Follow logs (Ctrl+C để thoát)
docker-compose logs -f app

# 100 dòng logs cuối
docker-compose logs --tail=100 app
```

### Vào bên trong container

```bash
# Vào PostgreSQL
docker exec -it commerce-postgres psql -U postgres -d commercegatewaydb

# Vào Redis
docker exec -it commerce-redis redis-cli

# Vào Spring Boot app (bash shell)
docker exec -it commerce-app sh
```

### Restart service

```bash
# Restart tất cả
docker-compose restart

# Restart 1 service
docker-compose restart app
```

### Xóa và làm mới

```bash
# Xóa tất cả containers, networks
docker-compose down

# Xóa thêm volumes (DATA)
docker-compose down -v

# Xóa thêm images
docker-compose down --rmi all

# Khởi động lại từ đầu
docker-compose up -d --build
```

---

## 🐛 Troubleshooting

### Lỗi 1: Port đã được sử dụng

```
Error: bind: address already in use
```

**Giải pháp:**
1. Kiểm tra port nào đang dùng:
   ```bash
   # Windows
   netstat -ano | findstr :8085
   netstat -ano | findstr :5432
   ```

2. Dừng process đang dùng port hoặc thay đổi port trong `docker-compose.yml`:
   ```yaml
   ports:
     - "8086:8085"  # Thay 8085 thành 8086
   ```

### Lỗi 2: Container không start được

```bash
# Xem logs chi tiết
docker-compose logs app

# Xem lý do container die
docker inspect commerce-app
```

**Nguyên nhân thường gặp:**
- Database chưa sẵn sàng → Chờ thêm vài giây và kiểm tra healthcheck
- Lỗi connection → Kiểm tra environment variables
- Lỗi build → Xem logs build: `docker-compose up --build`

### Lỗi 3: Database connection refused

**Giải pháp:**
1. Kiểm tra PostgreSQL đã chạy chưa:
   ```bash
   docker-compose ps postgres
   ```

2. Kiểm tra healthcheck:
   ```bash
   docker inspect commerce-postgres | findstr "Health"
   ```

3. Trong application.yml phải dùng tên service (không phải localhost):
   ```yaml
   datasource:
     url: jdbc:postgresql://postgres:5432/commercegatewaydb
     # ❌ KHÔNG DÙNG: jdbc:postgresql://localhost:5432/...
   ```

### Lỗi 4: Out of memory

```bash
# Tăng memory cho Docker Desktop (Windows):
# Docker Desktop → Settings → Resources → Memory → 4GB+
```

### Lỗi 5: Build quá lâu

**Giải pháp:**
1. Sử dụng `.dockerignore` (đã có sẵn)
2. Build không cần cache:
   ```bash
   docker-compose build --no-cache
   ```

---

## 📖 Workflow Development

### Quy trình làm việc hàng ngày:

```bash
# 1. Sáng: Bật tất cả services
docker-compose up -d

# 2. Coding... (thay đổi code)

# 3. Test thay đổi (rebuild app)
docker-compose up -d --build app

# 4. Xem logs nếu có lỗi
docker-compose logs -f app

# 5. Tối: Tắt services (hoặc để chạy qua đêm)
docker-compose down
```

### Development vs Production:

**Development (hiện tại):**
- Profile: `dev`
- Show SQL logs
- Hot reload (devtools)

**Production (sau này):**
- Thay đổi profile: `SPRING_PROFILES_ACTIVE: prod`
- Không show SQL
- Tối ưu JVM: `-Xmx2g -XX:+UseG1GC`

---

## 🎯 Best Practices

1. **Luôn dùng volumes cho data quan trọng**
   - Database → Volume
   - File uploads → Volume

2. **Healthcheck cho tất cả services**
   - Đảm bảo service sẵn sàng trước khi app connect

3. **Environment variables thay vì hardcode**
   - Dễ thay đổi giữa môi trường
   - Bảo mật thông tin nhạy cảm

4. **Networks riêng biệt**
   - Isolation và security

5. **Logs và monitoring**
   - Luôn check logs khi có vấn đề

---

## 📚 Tài liệu tham khảo

- [Docker Documentation](https://docs.docker.com/)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)

---

## ❓ Câu hỏi thường gặp

**Q: Tại sao phải dùng `postgres` thay vì `localhost` trong connection string?**
A: Trong Docker network, các containers giao tiếp qua tên service, không phải localhost.

**Q: Data có mất khi restart không?**
A: KHÔNG mất nếu dùng `docker-compose down` (không có `-v`). Volumes vẫn giữ nguyên.

**Q: Build lại image khi nào?**
A: Khi thay đổi code Java, dependencies (pom.xml), hoặc Dockerfile.

**Q: Có thể dev mà không dùng Docker không?**
A: Có, nhưng phải tự cài PostgreSQL, Redis, RabbitMQ trên máy local.

---

🎉 **Chúc bạn học Docker vui vẻ!** 🎉

