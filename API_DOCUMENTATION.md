# Sakila Film Management API Documentation

## Tổng quan
API REST để quản lý phim và ngôn ngữ trong database Sakila với các chức năng CRUD cơ bản và tìm kiếm.

## Base URLs
```
Film API: http://localhost:8080/api/films
Language API: http://localhost:8080/api/languages
Authentication API: http://localhost:8080/api/auth
```

## Authentication & Authorization
API sử dụng JWT (JSON Web Token) cho authentication và role-based authorization.

### Authentication
Token được trả về trong response và set vào HTTP-only cookie.

**Headers cho authenticated requests:**
```
Authorization: Bearer <jwt_token>
```

**Hoặc sử dụng cookie:**
```
Cookie: jwt=<jwt_token>
```

### Authorization - Role-Based Access Control (RBAC)

#### Roles trong hệ thống:
- **ADMIN**: Quyền cao nhất, có thể thực hiện tất cả operations
- **MANAGER**: Quyền trung bình, có thể CREATE và UPDATE nhưng không thể DELETE
- **USER**: Quyền thấp nhất, chỉ có thể READ

#### Phân quyền chi tiết:

**Film API:**
- **GET /api/films** - Public (không cần authentication)
- **GET /api/films/{id}** - Public (không cần authentication)
- **GET /api/films/search** - Public (không cần authentication)
- **POST /api/films** - ADMIN, MANAGER
- **PUT /api/films/{id}** - ADMIN, MANAGER
- **DELETE /api/films/{id}** - ADMIN only

**Language API:**
- **GET /api/languages** - Public (không cần authentication)
- **GET /api/languages/{id}** - Public (không cần authentication)
- **POST /api/languages** - ADMIN, MANAGER
- **DELETE /api/languages/{id}** - ADMIN only

#### Test Users:
- **Admin**: admin@sakila.com / password123 (ADMIN role)
- **Manager**: manager1@sakila.com / password123 (MANAGER role)
- **User**: user1@sakila.com / password123 (USER role)

## Film API Endpoints

### 1. Tạo phim mới (CREATE)
**POST** `/api/films`

**Request Body:**
```json
{
  "title": "The Matrix",
  "description": "A computer hacker learns about the true nature of reality.",
  "releaseYear": 1999,
  "languageId": 1,
  "originalLanguageId": 1,
  "rentalDuration": 3,
  "rentalRate": 4.99,
  "length": 136,
  "replacementCost": 19.99,
  "rating": "R",
  "specialFeatures": "Trailers,Commentaries"
}
```

**Response (201 Created):**
```json
{
  "filmId": 1001,
  "title": "The Matrix",
  "description": "A computer hacker learns about the true nature of reality.",
  "releaseYear": 1999,
  "languageName": "English",
  "originalLanguageName": "English",
  "rentalDuration": 3,
  "rentalRate": 4.99,
  "length": 136,
  "replacementCost": 19.99,
  "rating": "R",
  "specialFeatures": "Trailers,Commentaries",
  "lastUpdate": "2024-01-15T10:30:00"
}
```

### 2. Lấy tất cả phim (READ)
**GET** `/api/films`

**Query Parameters:**
- `page` (optional): Số trang (default: 0)
- `size` (optional): Số lượng phim mỗi trang (default: 10)
- `sortBy` (optional): Sắp xếp theo field (default: title)
- `sortDir` (optional): Hướng sắp xếp - asc/desc (default: asc)

**Example:**
```
GET /api/films?page=0&size=5&sortBy=title&sortDir=asc
```

**Response (200 OK):**
```json
[
  {
    "filmId": 1,
    "title": "ACADEMY DINOSAUR",
    "description": "A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies",
    "releaseYear": 2006,
    "languageName": "English",
    "originalLanguageName": null,
    "rentalDuration": 6,
    "rentalRate": 0.99,
    "length": 86,
    "replacementCost": 20.99,
    "rating": "PG",
    "specialFeatures": "Deleted Scenes,Behind the Scenes",
    "lastUpdate": "2006-02-15T05:03:42"
  }
]
```

### 3. Lấy phim theo ID (READ)
**GET** `/api/films/{id}`

**Example:**
```
GET /api/films/1
```

**Response (200 OK):**
```json
{
  "filmId": 1,
  "title": "ACADEMY DINOSAUR",
  "description": "A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies",
  "releaseYear": 2006,
  "languageName": "English",
  "originalLanguageName": null,
  "rentalDuration": 6,
  "rentalRate": 0.99,
  "length": 86,
  "replacementCost": 20.99,
  "rating": "PG",
  "specialFeatures": "Deleted Scenes,Behind the Scenes",
  "lastUpdate": "2006-02-15T05:03:42"
}
```

**Response (404 Not Found):**
```json
"Film not found with id: 9999"
```

### 4. Cập nhật phim (UPDATE)
**PUT** `/api/films/{id}`

**Request Body:**
```json
{
  "title": "The Matrix Reloaded",
  "description": "Neo and the rebel leaders estimate they have 72 hours until 250,000 probes discover Zion and destroy it and its inhabitants.",
  "releaseYear": 2003,
  "languageId": 1,
  "originalLanguageId": 1,
  "rentalDuration": 4,
  "rentalRate": 5.99,
  "length": 138,
  "replacementCost": 24.99,
  "rating": "R",
  "specialFeatures": "Trailers,Commentaries,Deleted Scenes"
}
```

**Response (200 OK):**
```json
{
  "filmId": 1,
  "title": "The Matrix Reloaded",
  "description": "Neo and the rebel leaders estimate they have 72 hours until 250,000 probes discover Zion and destroy it and its inhabitants.",
  "releaseYear": 2003,
  "languageName": "English",
  "originalLanguageName": "English",
  "rentalDuration": 4,
  "rentalRate": 5.99,
  "length": 138,
  "replacementCost": 24.99,
  "rating": "R",
  "specialFeatures": "Trailers,Commentaries,Deleted Scenes",
  "lastUpdate": "2024-01-15T10:35:00"
}
```

### 5. Xóa phim (DELETE)
**DELETE** `/api/films/{id}`

**Example:**
```
DELETE /api/films/1
```

**Response (204 No Content):**
```
(Empty body)
```

**Response (404 Not Found):**
```json
"Film not found with id: 9999"
```

### 6. Tìm kiếm phim theo tên
**GET** `/api/films/search`

**Query Parameters:**
- `title` (required): Từ khóa tìm kiếm

**Example:**
```
GET /api/films/search?title=matrix
```

**Response (200 OK):**
```json
[
  {
    "filmId": 1,
    "title": "The Matrix",
    "description": "A computer hacker learns about the true nature of reality.",
    "releaseYear": 1999,
    "languageName": "English",
    "originalLanguageName": "English",
    "rentalDuration": 3,
    "rentalRate": 4.99,
    "length": 136,
    "replacementCost": 19.99,
    "rating": "R",
    "specialFeatures": "Trailers,Commentaries",
    "lastUpdate": "2024-01-15T10:30:00"
  }
]
```

## Language API Endpoints

### 1. Tạo ngôn ngữ mới (CREATE)
**POST** `/api/languages`

**Request Body:**
```json
{
  "name": "Vietnamese"
}
```

**Response (201 Created):**
```json
{
  "languageId": 6,
  "name": "Vietnamese",
  "lastUpdate": "2025-10-27T03:54:31"
}
```

### 2. Lấy tất cả ngôn ngữ (READ)
**GET** `/api/languages`

**Query Parameters:**
- `page` (optional): Số trang (default: 0)
- `size` (optional): Số lượng ngôn ngữ mỗi trang (default: 10)
- `sortBy` (optional): Sắp xếp theo field (default: name)
- `sortDir` (optional): Hướng sắp xếp - asc/desc (default: asc)

**Example:**
```
GET /api/languages?page=0&size=5&sortBy=name&sortDir=asc
```

**Response (200 OK):**
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

### 3. Lấy ngôn ngữ theo ID (READ)
**GET** `/api/languages/{id}`

**Example:**
```
GET /api/languages/1
```

**Response (200 OK):**
```json
{
  "languageId": 1,
  "name": "English",
  "lastUpdate": "2025-10-25T02:19:00"
}
```

**Response (404 Not Found):**
```json
"Language not found with id: 999"
```

### 4. Xóa ngôn ngữ (DELETE) - Prevent Delete Strategy
**DELETE** `/api/languages/{id}`

**Example:**
```
DELETE /api/languages/1
```

**Nếu ngôn ngữ đang được sử dụng:**
```json
HTTP 409 Conflict
"Cannot delete language 'English' because it is being used by 5 film(s). Please reassign or delete those films first."
```

**Nếu ngôn ngữ không được sử dụng:**
```json
HTTP 204 No Content
```

## Authentication API Endpoints

### 1. Đăng ký tài khoản mới (REGISTER)
**POST** `/api/auth/register`

**Request Body:**
```json
{
  "username": "newuser",
  "email": "newuser@example.com",
  "password": "password123"
}
```

**Response (201 Created):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "newuser",
  "email": "newuser@example.com",
  "message": "User registered successfully"
}
```

**Response (400 Bad Request):**
```json
{
  "token": null,
  "type": "Bearer",
  "username": null,
  "email": null,
  "message": "Username is already taken!"
}
```

### 2. Đăng nhập (LOGIN)
**POST** `/api/auth/login`

**Request Body:**
```json
{
  "email": "newuser@example.com",
  "password": "password123"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "newuser",
  "email": "newuser@example.com",
  "message": "Login successful"
}
```

**Response (401 Unauthorized):**
```json
{
  "token": null,
  "type": "Bearer",
  "username": null,
  "email": null,
  "message": "Invalid email or password"
}
```

### 3. Đăng xuất (LOGOUT)
**POST** `/api/auth/logout`

**Response (200 OK):**
```json
{
  "token": null,
  "type": "Bearer",
  "username": null,
  "email": null,
  "message": "Logout successful"
}
```

**Note:** Token cũng được set vào HTTP-only cookie và sẽ được clear khi logout.

## Data Models

### FilmRequestDTO
```json
{
  "title": "string (required, max 255 chars)",
  "description": "string (optional)",
  "releaseYear": "integer (1900-2100)",
  "languageId": "byte (required)",
  "originalLanguageId": "byte (optional)",
  "rentalDuration": "byte (1-7, default: 3)",
  "rentalRate": "decimal (positive, default: 4.99)",
  "length": "short (min 1, optional)",
  "replacementCost": "decimal (positive, default: 19.99)",
  "rating": "enum (G, PG, PG-13, R, NC-17, default: G)",
  "specialFeatures": "string (optional)"
}
```

### LanguageRequestDTO
```json
{
  "name": "string (required, max 20 chars)"
}
```

### LanguageDTO
```json
{
  "languageId": "byte (auto-generated)",
  "name": "string",
  "lastUpdate": "datetime"
}
```

### LoginRequestDTO
```json
{
  "email": "string (required, valid email format)",
  "password": "string (required)"
}
```

### RegisterRequestDTO
```json
{
  "username": "string (required, 3-50 chars)",
  "email": "string (required, valid email format)",
  "password": "string (required, min 6 chars)"
}
```

### AuthResponseDTO
```json
{
  "token": "string (JWT token)",
  "type": "string (always 'Bearer')",
  "username": "string",
  "email": "string",
  "message": "string"
}
```

## Error Responses

### 400 Bad Request
```json
{
  "title": "Title is required",
  "releaseYear": "Release year must be after 1900",
  "rentalRate": "Rental rate must be positive",
  "name": "Language name is required",
  "email": "Email should be valid",
  "password": "Password must be at least 6 characters"
}
```

### 401 Unauthorized (Authentication)
```json
{
  "token": null,
  "type": "Bearer",
  "username": null,
  "email": null,
  "message": "Invalid email or password"
}
```

### 403 Forbidden (Authorization)
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 403,
  "error": "Forbidden",
  "message": "Access Denied",
  "path": "/api/films"
}
```

**Lý do 403 Forbidden:**
- User không có quyền thực hiện operation (ví dụ: USER role cố gắng CREATE/UPDATE/DELETE)
- Token hợp lệ nhưng không có role phù hợp

### 404 Not Found
```json
"Film not found with id: 9999"
```
hoặc
```json
"Language not found with id: 999"
```

### 409 Conflict (Language API)
```json
"Cannot delete language 'English' because it is being used by 5 film(s). Please reassign or delete those films first."
```

### 500 Internal Server Error
```json
"An error occurred: [error message]"
```

## Postman Collection

### Import Collection
1. Mở Postman
2. Click "Import"
3. Copy nội dung file JSON collection (sẽ được tạo bên dưới)

### Environment Variables
Tạo environment với các biến:
- `base_url`: `http://localhost:8080`
- `film_api`: `{{base_url}}/api/films`
- `language_api`: `{{base_url}}/api/languages`
- `auth_api`: `{{base_url}}/api/auth`

## Testing với Postman

### 1. Setup Environment
1. Tạo new Environment trong Postman
2. Thêm variable `base_url` = `http://localhost:8080`

### 2. Test CRUD Operations

#### Test CREATE
1. **Method**: POST
2. **URL**: `{{base_url}}/api/films`
3. **Headers**: `Content-Type: application/json`
4. **Body** (raw JSON):
```json
{
  "title": "Test Movie",
  "description": "A test movie for API testing",
  "releaseYear": 2024,
  "languageId": 1,
  "rentalDuration": 3,
  "rentalRate": 4.99,
  "length": 120,
  "replacementCost": 19.99,
  "rating": "PG"
}
```

#### Test READ (Get All)
1. **Method**: GET
2. **URL**: `{{base_url}}/api/films`

#### Test READ (Get By ID)
1. **Method**: GET
2. **URL**: `{{base_url}}/api/films/1`

#### Test UPDATE
1. **Method**: PUT
2. **URL**: `{{base_url}}/api/films/1`
3. **Headers**: `Content-Type: application/json`
4. **Body** (raw JSON):
```json
{
  "title": "Updated Test Movie",
  "description": "Updated description",
  "releaseYear": 2024,
  "languageId": 1,
  "rentalDuration": 4,
  "rentalRate": 5.99,
  "length": 130,
  "replacementCost": 24.99,
  "rating": "PG-13"
}
```

#### Test DELETE
1. **Method**: DELETE
2. **URL**: `{{base_url}}/api/films/1`

### 3. Test Search

#### Test Search
1. **Method**: GET
2. **URL**: `{{base_url}}/api/films/search?title=matrix`

### 4. Test Pagination
1. **Method**: GET
2. **URL**: `{{base_url}}/api/films?page=0&size=5&sortBy=title&sortDir=asc`

### 5. Test Language API

#### Test CREATE Language
1. **Method**: POST
2. **URL**: `{{base_url}}/api/languages`
3. **Headers**: `Content-Type: application/json`
4. **Body** (raw JSON):
```json
{
  "name": "Vietnamese"
}
```

#### Test READ Languages
1. **Method**: GET
2. **URL**: `{{base_url}}/api/languages`

#### Test READ Language by ID
1. **Method**: GET
2. **URL**: `{{base_url}}/api/languages/1`

#### Test DELETE Language
1. **Method**: DELETE
2. **URL**: `{{base_url}}/api/languages/1`
3. **Expected**: 409 Conflict nếu đang được sử dụng, 204 No Content nếu không

### 6. Test Authentication API

#### Test REGISTER
1. **Method**: POST
2. **URL**: `{{base_url}}/api/auth/register`
3. **Headers**: `Content-Type: application/json`
4. **Body** (raw JSON):
```json
{
  "username": "testuser",
  "email": "testuser@example.com",
  "password": "password123"
}
```

#### Test LOGIN
1. **Method**: POST
2. **URL**: `{{base_url}}/api/auth/login`
3. **Headers**: `Content-Type: application/json`
4. **Body** (raw JSON):
```json
{
  "email": "testuser@example.com",
  "password": "password123"
}
```

#### Test LOGOUT
1. **Method**: POST
2. **URL**: `{{base_url}}/api/auth/logout`
3. **Expected**: 200 OK với message "Logout successful"

## Sample Data

### Available Languages
- ID: 1, Name: English
- ID: 2, Name: Italian
- ID: 3, Name: Japanese
- ID: 4, Name: Mandarin
- ID: 5, Name: French
- ID: 6, Name: German

### Sample Users (for testing)
- **Username**: admin, **Email**: admin@sakila.com, **Password**: password123, **Role**: ADMIN
- **Username**: user1, **Email**: user1@sakila.com, **Password**: password123, **Role**: USER
- **Username**: manager1, **Email**: manager1@sakila.com, **Password**: password123, **Role**: MANAGER

### Available Ratings
- G (General Audiences)
- PG (Parental Guidance Suggested)
- PG-13 (Parents Strongly Cautioned)
- R (Restricted)
- NC-17 (No One 17 and Under Admitted)

### Special Features Options
- Trailers
- Commentaries
- Deleted Scenes
- Behind the Scenes

## Notes
- Tất cả timestamps đều theo format ISO 8601
- Decimal values sử dụng 2 chữ số thập phân
- API hỗ trợ CORS cho tất cả origins
- Pagination bắt đầu từ page 0
- Search không phân biệt hoa thường
- **Authentication**: Sử dụng JWT token, có thể gửi qua Authorization header hoặc HTTP-only cookie
- **Login**: Chỉ hỗ trợ đăng nhập bằng email (không hỗ trợ username)
- **Password**: Tối thiểu 6 ký tự, được hash bằng BCrypt
- **Token Expiration**: JWT token có thời hạn 24 giờ
- **Authorization**: Sử dụng role-based access control (RBAC) với 3 roles: ADMIN, MANAGER, USER
- **Public Endpoints**: Tất cả GET operations đều public, không cần authentication
- **Protected Endpoints**: CREATE/UPDATE cần ADMIN hoặc MANAGER role, DELETE chỉ cần ADMIN role
- **Error Handling**: 401 cho authentication issues, 403 cho authorization issues

---

# Authorization Testing Guide

## Tổng quan
Hướng dẫn test authorization cho Sakila Film Management API sau khi đã implement role-based access control.

## Test Cases

### Test Case 1: Public Access (Không cần authentication)
```bash
# Test GET operations - should work without authentication
curl -X GET http://localhost:8080/api/films
curl -X GET http://localhost:8080/api/films/1
curl -X GET "http://localhost:8080/api/films/search?title=matrix"
curl -X GET http://localhost:8080/api/languages
curl -X GET http://localhost:8080/api/languages/1
```

**Expected Result**: Tất cả requests đều thành công (200 OK)

### Test Case 2: Admin User Tests
```bash
# 1. Login as admin
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "admin@sakila.com", "password": "password123"}'

# 2. Extract JWT token from response and use it for authenticated requests
# Test CREATE Film (should work)
curl -X POST http://localhost:8080/api/films \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "title": "Test Movie Admin",
    "description": "A test movie created by admin",
    "releaseYear": 2024,
    "languageId": 1,
    "rentalDuration": 3,
    "rentalRate": 4.99,
    "length": 120,
    "replacementCost": 19.99,
    "rating": "PG"
  }'

# Test UPDATE Film (should work)
curl -X PUT http://localhost:8080/api/films/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "title": "Updated Movie by Admin",
    "description": "Updated by admin",
    "releaseYear": 2024,
    "languageId": 1,
    "rentalDuration": 4,
    "rentalRate": 5.99,
    "length": 130,
    "replacementCost": 24.99,
    "rating": "PG-13"
  }'

# Test DELETE Film (should work)
curl -X DELETE http://localhost:8080/api/films/1001 \
  -H "Authorization: Bearer <JWT_TOKEN>"

# Test CREATE Language (should work)
curl -X POST http://localhost:8080/api/languages \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{"name": "Korean"}'

# Test DELETE Language (should work)
curl -X DELETE http://localhost:8080/api/languages/7 \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

**Expected Result**: Tất cả requests đều thành công

### Test Case 3: Manager User Tests
```bash
# 1. Login as manager
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "manager1@sakila.com", "password": "password123"}'

# 2. Test CREATE Film (should work)
curl -X POST http://localhost:8080/api/films \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "title": "Test Movie Manager",
    "description": "A test movie created by manager",
    "releaseYear": 2024,
    "languageId": 1,
    "rentalDuration": 3,
    "rentalRate": 4.99,
    "length": 120,
    "replacementCost": 19.99,
    "rating": "PG"
  }'

# 3. Test UPDATE Film (should work)
curl -X PUT http://localhost:8080/api/films/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "title": "Updated Movie by Manager",
    "description": "Updated by manager",
    "releaseYear": 2024,
    "languageId": 1,
    "rentalDuration": 4,
    "rentalRate": 5.99,
    "length": 130,
    "replacementCost": 24.99,
    "rating": "PG-13"
  }'

# 4. Test DELETE Film (should fail - 403 Forbidden)
curl -X DELETE http://localhost:8080/api/films/1001 \
  -H "Authorization: Bearer <JWT_TOKEN>"

# 5. Test CREATE Language (should work)
curl -X POST http://localhost:8080/api/languages \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{"name": "Spanish"}'

# 6. Test DELETE Language (should fail - 403 Forbidden)
curl -X DELETE http://localhost:8080/api/languages/7 \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

**Expected Result**: 
- CREATE và UPDATE operations: Thành công
- DELETE operations: 403 Forbidden

### Test Case 4: Regular User Tests
```bash
# 1. Login as regular user
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "user1@sakila.com", "password": "password123"}'

# 2. Test CREATE Film (should fail - 403 Forbidden)
curl -X POST http://localhost:8080/api/films \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "title": "Test Movie User",
    "description": "A test movie created by user",
    "releaseYear": 2024,
    "languageId": 1,
    "rentalDuration": 3,
    "rentalRate": 4.99,
    "length": 120,
    "replacementCost": 19.99,
    "rating": "PG"
  }'

# 3. Test UPDATE Film (should fail - 403 Forbidden)
curl -X PUT http://localhost:8080/api/films/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "title": "Updated Movie by User",
    "description": "Updated by user",
    "releaseYear": 2024,
    "languageId": 1,
    "rentalDuration": 4,
    "rentalRate": 5.99,
    "length": 130,
    "replacementCost": 24.99,
    "rating": "PG-13"
  }'

# 4. Test DELETE Film (should fail - 403 Forbidden)
curl -X DELETE http://localhost:8080/api/films/1001 \
  -H "Authorization: Bearer <JWT_TOKEN>"

# 5. Test CREATE Language (should fail - 403 Forbidden)
curl -X POST http://localhost:8080/api/languages \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{"name": "Portuguese"}'

# 6. Test DELETE Language (should fail - 403 Forbidden)
curl -X DELETE http://localhost:8080/api/languages/7 \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

**Expected Result**: Tất cả requests đều fail với 403 Forbidden

### Test Case 5: Unauthenticated User Tests
```bash
# Test CREATE Film without authentication (should fail - 401 Unauthorized)
curl -X POST http://localhost:8080/api/films \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Test Movie No Auth",
    "description": "A test movie without auth",
    "releaseYear": 2024,
    "languageId": 1,
    "rentalDuration": 3,
    "rentalRate": 4.99,
    "length": 120,
    "replacementCost": 19.99,
    "rating": "PG"
  }'

# Test UPDATE Film without authentication (should fail - 401 Unauthorized)
curl -X PUT http://localhost:8080/api/films/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Updated Movie No Auth",
    "description": "Updated without auth",
    "releaseYear": 2024,
    "languageId": 1,
    "rentalDuration": 4,
    "rentalRate": 5.99,
    "length": 130,
    "replacementCost": 24.99,
    "rating": "PG-13"
  }'

# Test DELETE Film without authentication (should fail - 401 Unauthorized)
curl -X DELETE http://localhost:8080/api/films/1001

# Test CREATE Language without authentication (should fail - 401 Unauthorized)
curl -X POST http://localhost:8080/api/languages \
  -H "Content-Type: application/json" \
  -d '{"name": "Russian"}'

# Test DELETE Language without authentication (should fail - 401 Unauthorized)
curl -X DELETE http://localhost:8080/api/languages/7
```

**Expected Result**: Tất cả requests đều fail với 401 Unauthorized

## Postman Collection Test

### Environment Variables
Tạo environment với các biến:
- `base_url`: `http://localhost:8080`
- `admin_token`: JWT token của admin user
- `manager_token`: JWT token của manager user
- `user_token`: JWT token của regular user

### Test Collection Structure
1. **Authentication Tests**
   - Login Admin
   - Login Manager
   - Login User
   - Logout

2. **Public Access Tests**
   - GET All Films
   - GET Film by ID
   - Search Films
   - GET All Languages
   - GET Language by ID

3. **Admin Authorization Tests**
   - CREATE Film (Admin)
   - UPDATE Film (Admin)
   - DELETE Film (Admin)
   - CREATE Language (Admin)
   - DELETE Language (Admin)

4. **Manager Authorization Tests**
   - CREATE Film (Manager)
   - UPDATE Film (Manager)
   - DELETE Film (Manager) - Should Fail
   - CREATE Language (Manager)
   - DELETE Language (Manager) - Should Fail

5. **User Authorization Tests**
   - CREATE Film (User) - Should Fail
   - UPDATE Film (User) - Should Fail
   - DELETE Film (User) - Should Fail
   - CREATE Language (User) - Should Fail
   - DELETE Language (User) - Should Fail

6. **Unauthenticated Tests**
   - CREATE Film (No Auth) - Should Fail
   - UPDATE Film (No Auth) - Should Fail
   - DELETE Film (No Auth) - Should Fail
   - CREATE Language (No Auth) - Should Fail
   - DELETE Language (No Auth) - Should Fail

## Testing Notes

- Tất cả passwords đều là `password123` và được hash bằng BCrypt
- JWT token có thời hạn 24 giờ
- Token có thể được gửi qua Authorization header hoặc HTTP-only cookie
- Các READ operations (GET) luôn public, không cần authentication
- Các WRITE operations (POST, PUT, DELETE) cần authentication và authorization phù hợp
