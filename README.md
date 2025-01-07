# ClamAV Scanner Project

## Mô tả
Dự án **ClamAV Scanner** được xây dựng để tích hợp và quản lý công cụ quét virus **ClamAV** trên hệ điều hành Linux. Giao diện đồ họa (GUI) được phát triển bằng Java Swing giúp người dùng dễ dàng sử dụng và quản lý các hoạt động quét file hoặc thư mục.

---

## Chức năng chính
1. **Quét Virus**:
   - Chọn file hoặc thư mục để quét virus.
   - Hiển thị danh sách các file bị nhiễm và loại virus.

2. **Biểu đồ**:
   - Hiển thị biểu đồ kết quả quét với số lượng file sạch và bị nhiễm.

3. **Xuất Dữ Liệu**:
   - Xuất kết quả quét ra file Excel.
   - Xuất biểu đồ kết quả quét ra file hình ảnh.

4. **Theo dõi tiến trình**:
   - Hiển thị tiến trình quét và thông tin file đang được quét.

---

## Yêu cầu hệ thống
- **Hệ điều hành**: Linux (Hỗ trợ sử dụng ClamAV)
- **Java JDK**: Phiên bản 8 trở lên
- **ClamAV**: Đã được cài đặt trên hệ thống
- **Thư viện bổ sung**:
  - JFreeChart (dùng để vẽ biểu đồ)
  - Apache POI (dùng để xuất file Excel)

---

## Hướng dẫn sử dụng
1. **Cài đặt ClamAV trên Linux**:
   ```bash
   sudo apt update
   sudo apt install clamav
   sudo freshclam
Build và chạy ứng dụng:

Biên dịch và đóng gói ứng dụng:
bash
Copy code
mvn clean package
Chạy ứng dụng:
bash
Copy code
java -jar target/clamav-project-1.0-SNAPSHOT.jar
Chọn file hoặc thư mục:

Nhấn nút Choose File/Directory để chọn file hoặc thư mục cần quét.
Quét virus:

Nhấn nút Scan để bắt đầu quét. Tiến trình quét sẽ được hiển thị trong thanh tiến trình.
Xuất kết quả:

Nhấn nút Export to Excel để xuất kết quả quét ra file Excel tại thư mục result.
Xem biểu đồ:

Biểu đồ kết quả sẽ hiển thị bên phải giao diện. File hình ảnh biểu đồ sẽ được lưu tại thư mục result.
Cấu trúc dự án
bash
Copy code
clamav-project/
├── src/main/java/clamav/
│   ├── chart/               # Lớp vẽ biểu đồ
│   │   └── ChartGenerator.java
│   ├── export/              # Lớp xuất dữ liệu
│   │   └── FileExporter.java
│   ├── processor/           # Xử lý kết quả quét
│   │   └── ScanResultProcessor.java
│   ├── scanner/             # Tích hợp ClamAV
│   │   ├── ClamAVScanner.java
│   │   └── FileUtils.java
│   └── MainGUI.java         # Giao diện chính
├── result/                  # Lưu kết quả (Excel, biểu đồ)
├── pom.xml                  # Cấu hình Maven
Ghi chú
Ứng dụng chỉ chạy trên hệ điều hành Linux do phụ thuộc vào ClamAV.
Nếu gặp lỗi khi quét, vui lòng kiểm tra cài đặt ClamAV hoặc cấp quyền cho file.
