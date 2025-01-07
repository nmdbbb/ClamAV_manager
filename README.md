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

2. Build và chạy ứng dụng:
Biên dịch và đóng gói ứng dụng:
bash
Copy code
mvn clean package
Chạy ứng dụng:
bash
Copy code
java -jar target/clamav-project-1.0-SNAPSHOT.jar
3. Thao tác trong giao diện:
Quét virus:
Nhấn nút Choose File/Directory để chọn thư mục hoặc file cần quét.
Nhấn nút Scan để bắt đầu quét.
Trong khi quét, thanh tiến trình sẽ hiển thị trạng thái và file hiện tại đang được quét.
Sau khi quét xong:
Hiển thị danh sách các file bị nhiễm virus kèm loại virus.
Hiển thị tổng số file sạch và file bị nhiễm trên biểu đồ.
Xuất dữ liệu:
Nhấn nút Export to Excel để xuất kết quả quét ra file Excel.
File kết quả sẽ được lưu tại thư mục result với tên scan_results.xlsx.
Xem biểu đồ:
Biểu đồ kết quả (hiển thị số file sạch và số file bị nhiễm) sẽ hiển thị bên phải giao diện.
File ảnh biểu đồ sẽ được tự động lưu tại thư mục result với tên scan_chart.png.
Cấu trúc dự án
plaintext
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
│   ├── scan_results.xlsx    # File Excel kết quả quét
│   └── scan_chart.png       # File ảnh biểu đồ
├── pom.xml                  # Cấu hình Maven
├── target/                  # File biên dịch (JAR)
