# Sakila Database - Phân tích cấu trúc và quan hệ

## Tổng quan
Sakila là một cơ sở dữ liệu mẫu được thiết kế để mô phỏng hệ thống quản lý cửa hàng cho thuê DVD. Database này chứa dữ liệu về phim, diễn viên, khách hàng, nhân viên, cửa hàng và các giao dịch cho thuê.

## Danh sách các bảng

### 1. **actor** - Diễn viên
- `actor_id` (PK) - ID diễn viên
- `first_name` - Tên
- `last_name` - Họ
- `last_update` - Thời gian cập nhật cuối

### 2. **address** - Địa chỉ
- `address_id` (PK) - ID địa chỉ
- `address` - Địa chỉ
- `address2` - Địa chỉ phụ
- `district` - Quận/Huyện
- `city_id` (FK) - ID thành phố
- `postal_code` - Mã bưu điện
- `phone` - Số điện thoại
- `last_update` - Thời gian cập nhật cuối

### 3. **category** - Thể loại phim
- `category_id` (PK) - ID thể loại
- `name` - Tên thể loại
- `last_update` - Thời gian cập nhật cuối

### 4. **city** - Thành phố
- `city_id` (PK) - ID thành phố
- `city` - Tên thành phố
- `country_id` (FK) - ID quốc gia
- `last_update` - Thời gian cập nhật cuối

### 5. **country** - Quốc gia
- `country_id` (PK) - ID quốc gia
- `country` - Tên quốc gia
- `last_update` - Thời gian cập nhật cuối

### 6. **customer** - Khách hàng
- `customer_id` (PK) - ID khách hàng
- `store_id` (FK) - ID cửa hàng
- `first_name` - Tên
- `last_name` - Họ
- `email` - Email
- `address_id` (FK) - ID địa chỉ
- `active` - Trạng thái hoạt động
- `create_date` - Ngày tạo
- `last_update` - Thời gian cập nhật cuối

### 7. **film** - Phim
- `film_id` (PK) - ID phim
- `title` - Tiêu đề phim
- `description` - Mô tả
- `release_year` - Năm phát hành
- `language_id` (FK) - ID ngôn ngữ
- `original_language_id` (FK) - ID ngôn ngữ gốc
- `rental_duration` - Thời gian cho thuê
- `rental_rate` - Giá cho thuê
- `length` - Độ dài phim
- `replacement_cost` - Chi phí thay thế
- `rating` - Xếp hạng
- `special_features` - Tính năng đặc biệt
- `last_update` - Thời gian cập nhật cuối

### 8. **film_actor** - Diễn viên trong phim (Bảng liên kết)
- `actor_id` (FK) - ID diễn viên
- `film_id` (FK) - ID phim
- `last_update` - Thời gian cập nhật cuối

### 9. **film_category** - Thể loại phim (Bảng liên kết)
- `film_id` (FK) - ID phim
- `category_id` (FK) - ID thể loại
- `last_update` - Thời gian cập nhật cuối

### 10. **film_text** - Văn bản phim (Full-text search)
- `film_id` (PK) - ID phim
- `title` - Tiêu đề phim
- `description` - Mô tả

### 11. **inventory** - Kho hàng
- `inventory_id` (PK) - ID kho hàng
- `film_id` (FK) - ID phim
- `store_id` (FK) - ID cửa hàng
- `last_update` - Thời gian cập nhật cuối

### 12. **language** - Ngôn ngữ
- `language_id` (PK) - ID ngôn ngữ
- `name` - Tên ngôn ngữ
- `last_update` - Thời gian cập nhật cuối

### 13. **payment** - Thanh toán
- `payment_id` (PK) - ID thanh toán
- `customer_id` (FK) - ID khách hàng
- `staff_id` (FK) - ID nhân viên
- `rental_id` (FK) - ID cho thuê
- `amount` - Số tiền
- `payment_date` - Ngày thanh toán
- `last_update` - Thời gian cập nhật cuối

### 14. **rental** - Cho thuê
- `rental_id` (PK) - ID cho thuê
- `rental_date` - Ngày cho thuê
- `inventory_id` (FK) - ID kho hàng
- `customer_id` (FK) - ID khách hàng
- `return_date` - Ngày trả
- `staff_id` (FK) - ID nhân viên
- `last_update` - Thời gian cập nhật cuối

### 15. **staff** - Nhân viên
- `staff_id` (PK) - ID nhân viên
- `first_name` - Tên
- `last_name` - Họ
- `address_id` (FK) - ID địa chỉ
- `picture` - Ảnh
- `email` - Email
- `store_id` (FK) - ID cửa hàng
- `active` - Trạng thái hoạt động
- `username` - Tên đăng nhập
- `password` - Mật khẩu
- `last_update` - Thời gian cập nhật cuối

### 16. **store** - Cửa hàng
- `store_id` (PK) - ID cửa hàng
- `manager_staff_id` (FK) - ID nhân viên quản lý
- `address_id` (FK) - ID địa chỉ
- `last_update` - Thời gian cập nhật cuối

## Quan hệ giữa các bảng

### Quan hệ 1-1 (One-to-One)
- **store** ↔ **staff**: Mỗi cửa hàng có một quản lý duy nhất

### Quan hệ 1-N (One-to-Many)
- **country** → **city**: Một quốc gia có nhiều thành phố
- **city** → **address**: Một thành phố có nhiều địa chỉ
- **address** → **customer**: Một địa chỉ có thể thuộc về nhiều khách hàng
- **address** → **staff**: Một địa chỉ có thể thuộc về nhiều nhân viên
- **address** → **store**: Một địa chỉ có thể có nhiều cửa hàng
- **store** → **customer**: Một cửa hàng có nhiều khách hàng
- **store** → **staff**: Một cửa hàng có nhiều nhân viên
- **store** → **inventory**: Một cửa hàng có nhiều mặt hàng trong kho
- **language** → **film**: Một ngôn ngữ có nhiều phim
- **film** → **inventory**: Một phim có nhiều bản sao trong kho
- **film** → **rental**: Một phim có thể được thuê nhiều lần
- **customer** → **rental**: Một khách hàng có thể thuê nhiều phim
- **customer** → **payment**: Một khách hàng có nhiều thanh toán
- **staff** → **rental**: Một nhân viên xử lý nhiều giao dịch cho thuê
- **staff** → **payment**: Một nhân viên xử lý nhiều thanh toán
- **rental** → **payment**: Một giao dịch cho thuê có thể có nhiều thanh toán

### Quan hệ N-N (Many-to-Many)
- **actor** ↔ **film**: Nhiều diễn viên có thể đóng nhiều phim (qua bảng `film_actor`)
- **film** ↔ **category**: Một phim có thể thuộc nhiều thể loại (qua bảng `film_category`)

## Sơ đồ quan hệ chính

```
country (1) → (N) city (1) → (N) address (1) → (N) customer
                                                      ↓
                                              store (1) → (N) staff
                                                      ↓
                                              inventory (1) → (N) rental
                                                      ↓
                                              payment ← (N) customer
                                                      ↓
                                              staff (1) → (N) rental
                                                      ↓
                                              payment ← (N) staff

film (1) → (N) inventory
  ↓
film_actor (N) ← actor

film (N) → (N) category (qua film_category)
```

## Các view có sẵn
Database cũng chứa các view để truy vấn dữ liệu phức tạp:
- **actor_info**: Thông tin chi tiết về diễn viên và các phim họ đóng
- Các view khác để hỗ trợ báo cáo và thống kê

## Mục đích sử dụng
Database Sakila được thiết kế để:
- Quản lý thông tin phim và diễn viên
- Theo dõi kho hàng tại các cửa hàng
- Quản lý khách hàng và nhân viên
- Xử lý giao dịch cho thuê và thanh toán
- Tạo báo cáo thống kê về doanh thu và hoạt động kinh doanh

## Lưu ý kỹ thuật
- Sử dụng InnoDB engine với hỗ trợ foreign key constraints
- Có đầy đủ indexes để tối ưu hiệu suất truy vấn
- Sử dụng charset UTF-8 để hỗ trợ đa ngôn ngữ
- Có trigger và stored procedures để tự động cập nhật `last_update`
