# API Security - Hướng dẫn Cơ bản

## Tổng quan
API Security là việc bảo vệ các Application Programming Interface (API) khỏi các mối đe dọa bảo mật. APIs là cầu nối quan trọng giữa các hệ thống và ứng dụng, nên việc bảo mật chúng là rất quan trọng.

## 10 Nguyên tắc Bảo mật API Cơ bản

### 1. **Xác thực và Ủy quyền (Authentication & Authorization)**
- Sử dụng OAuth 2.0 hoặc OpenID Connect
- Áp dụng RBAC (Role-Based Access Control)
- Luôn xác thực mọi request, không tin tưởng mặc định

### 2. **Mã hóa Dữ liệu**
- **HTTPS/TLS**: Bắt buộc cho mọi API endpoint
- **Mã hóa lưu trữ**: Sử dụng AES-256 cho dữ liệu nhạy cảm
- **Hash passwords**: Sử dụng bcrypt hoặc Argon2

### 3. **API Gateway**
- Điểm truy cập duy nhất cho tất cả API
- Thực thi chính sách bảo mật tập trung
- Rate limiting và monitoring

### 4. **Rate Limiting & Throttling**
- Giới hạn số request từ một IP/user
- Ngăn chặn DoS attacks
- Bảo vệ tài nguyên hệ thống

### 5. **Input Validation**
- Kiểm tra và validate mọi input
- Ngăn chặn SQL Injection, XSS
- Sử dụng whitelist thay vì blacklist

### 6. **API Keys & Tokens Management**
- Không lưu keys trong source code
- Sử dụng secret management tools
- Xoay vòng keys định kỳ

### 7. **Logging & Monitoring**
- Log tất cả API requests
- Monitor các hoạt động bất thường
- Alert khi có suspicious activity

### 8. **Error Handling**
- Không expose thông tin nhạy cảm trong error messages
- Sử dụng generic error responses
- Log chi tiết cho debugging

### 9. **CORS Configuration**
- Cấu hình CORS chặt chẽ
- Chỉ allow các domain cần thiết
- Không sử dụng wildcard (*) trong production

### 10. **Regular Updates**
- Cập nhật dependencies thường xuyên
- Patch security vulnerabilities
- Security testing định kỳ

## Các Lỗ hổng Bảo mật API Phổ biến

### 1. **Broken Object Level Authorization (BOLA)**
- Kiểm tra quyền truy cập object
- Validate user có quyền access resource không

### 2. **Broken Authentication**
- Implement strong authentication
- Secure session management
- Multi-factor authentication

### 3. **Excessive Data Exposure**
- Chỉ return data cần thiết
- Filter sensitive information
- Sử dụng DTOs/ViewModels

### 4. **Lack of Rate Limiting**
- Implement rate limiting
- Different limits cho different endpoints
- Monitor và adjust limits

### 5. **Broken Function Level Authorization**
- Check permissions cho mỗi function
- Role-based access control
- Principle of least privilege

## Tools & Technologies

### Authentication
- **OAuth 2.0**: Industry standard
- **JWT**: Stateless authentication
- **API Keys**: Simple authentication

### Security Tools
- **API Gateway**: Kong, AWS API Gateway
- **WAF**: Web Application Firewall
- **Rate Limiting**: Redis, NGINX
- **Monitoring**: ELK Stack, Prometheus

## Best Practices Checklist

- [ ] Sử dụng HTTPS cho tất cả endpoints
- [ ] Implement proper authentication
- [ ] Validate và sanitize input
- [ ] Implement rate limiting
- [ ] Log và monitor activities
- [ ] Regular security testing
- [ ] Keep dependencies updated
- [ ] Use API versioning
- [ ] Document security requirements
- [ ] Implement proper error handling

---

# Language API Documentation

## Tổng quan
Language API được implement với phương pháp **Prevent Delete** để đảm bảo tính toàn vẹn dữ liệu khi xóa Language đang được sử dụng bởi Film.

## Các API Endpoints

### 1. CREATE Language
```http
POST /api/languages
Content-Type: application/json

{
  "name": "Vietnamese"
}
```

**Response:**
```json
{
  "languageId": 6,
  "name": "Vietnamese",
  "lastUpdate": "2025-10-27T03:54:31"
}
```

### 2. READ All Languages
```http
GET /api/languages?page=0&size=10&sortBy=name&sortDir=asc
```

**Response:**
```json
[
  {
    "languageId": 1,
    "name": "English",
    "lastUpdate": "2025-10-25T02:19:00"
  },
  {
    "languageId": 2,
    "name": "Italian",
    "lastUpdate": "2025-10-25T02:19:00"
  }
]
```

### 3. READ Language by ID
```http
GET /api/languages/1
```

**Response:**
```json
{
  "languageId": 1,
  "name": "English",
  "lastUpdate": "2025-10-25T02:19:00"
}
```

### 4. DELETE Language (với Prevent Delete)
```http
DELETE /api/languages/1
```

**Nếu Language đang được sử dụng:**
```json
HTTP 409 Conflict
"Cannot delete language 'English' because it is being used by 5 film(s). Please reassign or delete those films first."
```

**Nếu Language không được sử dụng:**
```json
HTTP 204 No Content
```

## Prevent Delete Strategy

### Cách hoạt động:
1. **Kiểm tra trước khi xóa**: API sẽ kiểm tra xem Language có đang được sử dụng bởi Film nào không
2. **Đếm usage**: Đếm cả primary language và original language usage
3. **Ngăn chặn xóa**: Nếu có Film đang sử dụng → trả về lỗi 409 Conflict
4. **Cho phép xóa**: Nếu không có Film nào sử dụng → xóa thành công

### Các trường hợp kiểm tra:
- **Primary Language**: Film.language_id = Language.language_id
- **Original Language**: Film.original_language_id = Language.language_id

### Exception Handling:
- `IllegalStateException` → HTTP 409 Conflict (không thể xóa)
- `IllegalArgumentException` → HTTP 400 Bad Request (tên trùng lặp)
- `ResourceNotFoundException` → HTTP 404 Not Found (không tìm thấy)

## Test Cases

### Test Case 1: Tạo Language mới
```bash
curl -X POST http://localhost:8080/api/languages \
  -H "Content-Type: application/json" \
  -d '{"name": "Vietnamese"}'
```

### Test Case 2: Thử xóa Language đang được sử dụng
```bash
curl -X DELETE http://localhost:8080/api/languages/1
# Expected: 409 Conflict
```

### Test Case 3: Xóa Language không được sử dụng
```bash
curl -X DELETE http://localhost:8080/api/languages/6
# Expected: 204 No Content
```

## Lợi ích của Prevent Delete Strategy

1. **Data Integrity**: Đảm bảo không mất dữ liệu quan trọng
2. **Clear Error Messages**: Thông báo lỗi rõ ràng về lý do không thể xóa
3. **Usage Information**: Cung cấp thông tin chi tiết về số lượng Film đang sử dụng
4. **Safe Operation**: Ngăn chặn các thao tác có thể gây hại đến dữ liệu

## Các phương pháp khác (không được implement)

### 1. Cascade Delete
- **Ưu điểm**: Đơn giản, tự động
- **Nhược điểm**: Mất dữ liệu Film quan trọng
- **Không khuyến nghị** cho production

### 2. Soft Delete
- **Ưu điểm**: Khôi phục được dữ liệu
- **Nhược điểm**: Phức tạp, cần thêm field deleted
- **Có thể implement** nếu cần

### 3. Reassign Strategy
- **Ưu điểm**: Giữ nguyên dữ liệu Film
- **Nhược điểm**: Phức tạp, cần API reassign
- **Có thể implement** nếu cần