# 🚀 DOCKER CHEAT SHEET - LỆNH NHANH

## ⚡ Lệnh cơ bản (dùng hàng ngày)

```bash
# Khởi động tất cả
docker-compose up -d

# Xem logs
docker-compose logs -f app

# Dừng tất cả
docker-compose down

# Rebuild khi thay đổi code
docker-compose up -d --build app
```

## 📊 Xem trạng thái

```bash
# Danh sách containers
docker-compose ps

# Xem resource usage (CPU, RAM)
docker stats

# Xem logs
docker-compose logs --tail=50 app
```

## 🔄 Restart & Rebuild

```bash
# Restart 1 service
docker-compose restart app

# Rebuild toàn bộ
docker-compose up -d --build

# Rebuild không cache (chậm nhưng chắc chắn)
docker-compose build --no-cache
```

## 🗑️ Dọn dẹp

```bash
# Xóa containers (GIỮ data)
docker-compose down

# Xóa containers + volumes (MẤT data)
docker-compose down -v

# Xóa containers + images
docker-compose down --rmi all

# Dọn sạch toàn bộ Docker
docker system prune -a --volumes
```

## 🐞 Debug

```bash
# Vào PostgreSQL
docker exec -it commerce-postgres psql -U postgres -d commercegatewaydb

# Vào Redis
docker exec -it commerce-redis redis-cli

# Vào container app
docker exec -it commerce-app sh

# Xem logs chi tiết
docker inspect commerce-app
```

## 🔍 Kiểm tra

```bash
# Test PostgreSQL connection
docker exec commerce-postgres pg_isready -U postgres

# Test Redis
docker exec commerce-redis redis-cli ping

# Check healthcheck status
docker inspect --format='{{json .State.Health}}' commerce-app
```

## 🌐 Truy cập

- API: http://localhost:8085
- Swagger: http://localhost:8085/swagger-ui.html
- RabbitMQ: http://localhost:15672 (admin/admin123)
- PostgreSQL: localhost:5432 (postgres/12345)
- Redis: localhost:6379

## ⚠️ Xử lý lỗi thường gặp

```bash
# Port bị chiếm
netstat -ano | findstr :8085

# Container không start
docker-compose logs app

# Database connection failed
docker-compose restart postgres
docker-compose logs postgres

# Out of memory
# → Docker Desktop Settings → Resources → Tăng Memory lên 4GB

# Build quá lâu
docker-compose build --no-cache --pull
```

