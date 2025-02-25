# Zalo Clone - Ứng dụng nhắn tin (Java Android)

**Zalo Clone** là một ứng dụng nhắn tin đơn giản được phát triển bằng Java cho nền tảng Android, mô phỏng một số tính năng cơ bản của ứng dụng Zalo. Ứng dụng này cho phép người dùng đăng ký, đăng nhập, nhắn tin, và quản lý danh bạ.

## Tính năng chính

- **Đăng ký/Đăng nhập**: Người dùng có thể tạo tài khoản mới và đăng nhập vào hệ thống.
- **Nhắn tin**: Người dùng có thể gửi và nhận tin nhắn văn bản với các người dùng khác.
- **Danh bạ**: Quản lý danh sách bạn bè và xem trạng thái hoạt động của họ.
- **Trạng thái hoạt động**: Hiển thị trạng thái online/offline của người dùng.
- **Đơn giản và dễ sử dụng**: Giao diện thân thiện, dễ dàng sử dụng.

## Công nghệ sử dụng

- **Ngôn ngữ**: Java
- **Platform**: Android
- **Database**: Firebase Realtime Database (hoặc SQLite nếu sử dụng local database)
- **Authentication**: Firebase Authentication (hoặc tự xây dựng hệ thống xác thực)
- **Push Notification**: Firebase Cloud Messaging (FCM) (nếu có)
- **UI**: XML layout, Material Design Components

## Cài đặt và chạy dự án

### Yêu cầu hệ thống

- Android Studio (phiên bản mới nhất)
- JDK 11 trở lên
- Android SDK (API level 21 trở lên)

### Các bước cài đặt

1. **Clone dự án**:
   ```bash
   git clone https://github.com/tranlequocthong313/zalo.git
   cd zalo
   ```

2. **Mở dự án trong Android Studio**:
   - Mở Android Studio và chọn `Open an existing Android Studio project`.
   - Dẫn đến thư mục chứa dự án vừa clone và chọn `OK`.

3. **Cấu hình Firebase** (nếu sử dụng Firebase):
   - Tạo một dự án trên [Firebase Console](https://console.firebase.google.com/).
   - Tải file `google-services.json` và đặt vào thư mục `app/` của dự án.
   - Thêm các dependencies cần thiết vào file `build.gradle`:
     ```gradle
     implementation 'com.google.firebase:firebase-database:20.0.3'
     implementation 'com.google.firebase:firebase-auth:21.0.1'
     implementation 'com.google.firebase:firebase-messaging:23.0.0'
     ```

4. **Chạy ứng dụng**:
   - Kết nối thiết bị Android hoặc sử dụng máy ảo (emulator).
   - Nhấn `Run` trong Android Studio để biên dịch và chạy ứng dụng.

## Cấu trúc thư mục

```
zalo/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/zalo/
│   │   │   │   ├── activity/          # Các activity (màn hình)
│   │   │   │   ├── adapter/           # Các adapter cho RecyclerView, ListView
│   │   │   │   ├── model/             # Các model dữ liệu
│   │   │   │   ├── service/           # Các service xử lý logic (API, Firebase, v.v.)
│   │   │   │   ├── utils/             # Các tiện ích (helper, common functions)
│   │   │   │   └── ZaloApplication.java # Lớp Application chính
│   │   │   ├── res/                   # Tài nguyên (layout, drawable, string, v.v.)
│   │   │   └── AndroidManifest.xml    # File cấu hình Android
│   ├── build.gradle                   # Cấu hình build cho module app
├── build.gradle                       # Cấu hình build cho toàn bộ dự án
└── settings.gradle                    # Cấu hình settings cho dự án
```

## Các màn hình chính

- **Màn hình đăng ký/đăng nhập**: Cho phép người dùng đăng ký tài khoản mới hoặc đăng nhập vào hệ thống.
- **Màn hình danh bạ**: Hiển thị danh sách bạn bè và trạng thái hoạt động của họ.
- **Màn hình chat**: Cho phép người dùng nhắn tin với bạn bè.
- **Màn hình cài đặt**: Cho phép người dùng cập nhật thông tin cá nhân và cài đặt tài khoản.

## Kết nối với Firebase

Ứng dụng sử dụng Firebase để quản lý dữ liệu và xác thực người dùng. Các chức năng chính bao gồm:

- **Firebase Authentication**: Quản lý đăng ký và đăng nhập người dùng.
- **Firebase Realtime Database**: Lưu trữ và đồng bộ dữ liệu tin nhắn, danh bạ.
- **Firebase Cloud Messaging**: Gửi thông báo khi có tin nhắn mới (nếu có).

## Đóng góp

Nếu bạn muốn đóng góp vào dự án, vui lòng làm theo các bước sau:

1. Fork dự án
2. Tạo branch mới (`git checkout -b feature/YourFeatureName`)
3. Commit các thay đổi (`git commit -m 'Add some feature'`)
4. Push lên branch (`git push origin feature/YourFeatureName`)
5. Mở một Pull Request

## Liên hệ

Nếu bạn có bất kỳ câu hỏi hoặc góp ý nào, vui lòng liên hệ:

- **Tên**: Trần Lê Quốc Thông
- **Email**: tranlequocthong313@gmail.com
- **GitHub**: [tranlequocthong313](https://github.com/tranlequocthong313)
