# Sakila Film Management API Documentation

## Tổng quan
API REST để quản lý phim trong database Sakila với các chức năng CRUD cơ bản và tìm kiếm nâng cao.

## Base URL
```
http://localhost:8080/api/films
```

## Authentication
Hiện tại API không yêu cầu authentication.

## API Endpoints

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

## Bonus Endpoints

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

### 7. Lọc phim với nhiều điều kiện
**GET** `/api/films/filter`

**Query Parameters:**
- `title` (optional): Tên phim
- `year` (optional): Năm phát hành
- `rating` (optional): Xếp hạng (G, PG, PG-13, R, NC-17)
- `languageId` (optional): ID ngôn ngữ

**Example:**
```
GET /api/films/filter?year=2006&rating=PG&languageId=1
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

### 8. Lấy phim theo năm
**GET** `/api/films/year/{year}`

**Example:**
```
GET /api/films/year/2006
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

### 9. Lấy phim theo rating
**GET** `/api/films/rating/{rating}`

**Example:**
```
GET /api/films/rating/PG
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

### 10. Lấy phim theo khoảng giá thuê
**GET** `/api/films/rental-rate`

**Query Parameters:**
- `minRate` (required): Giá thuê tối thiểu
- `maxRate` (required): Giá thuê tối đa

**Example:**
```
GET /api/films/rental-rate?minRate=2.99&maxRate=4.99
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
    "rentalRate": 4.99,
    "length": 86,
    "replacementCost": 20.99,
    "rating": "PG",
    "specialFeatures": "Deleted Scenes,Behind the Scenes",
    "lastUpdate": "2006-02-15T05:03:42"
  }
]
```

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

### FilmDTO
```json
{
  "filmId": "short (auto-generated)",
  "title": "string",
  "description": "string",
  "releaseYear": "integer",
  "languageName": "string",
  "originalLanguageName": "string",
  "rentalDuration": "byte",
  "rentalRate": "decimal",
  "length": "short",
  "replacementCost": "decimal",
  "rating": "enum",
  "specialFeatures": "string",
  "lastUpdate": "datetime"
}
```

## Error Responses

### 400 Bad Request
```json
{
  "title": "Title is required",
  "releaseYear": "Release year must be after 1900",
  "rentalRate": "Rental rate must be positive"
}
```

### 404 Not Found
```json
"Film not found with id: 9999"
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
- `api_base`: `{{base_url}}/api/films`

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

### 3. Test Search & Filter

#### Test Search
1. **Method**: GET
2. **URL**: `{{base_url}}/api/films/search?title=matrix`

#### Test Filter
1. **Method**: GET
2. **URL**: `{{base_url}}/api/films/filter?year=2006&rating=PG`

### 4. Test Pagination
1. **Method**: GET
2. **URL**: `{{base_url}}/api/films?page=0&size=5&sortBy=title&sortDir=asc`

## Sample Data

### Available Languages
- ID: 1, Name: English
- ID: 2, Name: Italian
- ID: 3, Name: Japanese
- ID: 4, Name: Mandarin
- ID: 5, Name: French
- ID: 6, Name: German

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
