ClamAV Scanner
Mô tả
ClamAV Scanner là một ứng dụng Java được phát triển để chạy trên hệ điều hành Linux. Ứng dụng tích hợp với ClamAV, một công cụ mã nguồn mở, để quét các tệp và thư mục nhằm phát hiện các tệp bị nhiễm virus. Kết quả quét được hiển thị thông qua giao diện đồ họa người dùng (GUI).

Ứng dụng hỗ trợ:

Quét virus từ các tệp và thư mục cụ thể.
Hiển thị kết quả quét trong bảng và biểu đồ.
Xuất kết quả quét ra tệp Excel để lưu trữ.
Chức năng chính
Quét Virus: Tích hợp với ClamAV để quét và phân loại tệp.
Hiển thị Kết Quả: Hiển thị danh sách tệp bị nhiễm và tệp sạch trong bảng.
Biểu đồ: Hiển thị số lượng tệp sạch và tệp bị nhiễm dưới dạng biểu đồ thanh.
Xuất Kết Quả: Xuất dữ liệu kết quả ra tệp Excel.
Yêu cầu hệ thống
Hệ điều hành: Linux (Ubuntu, Debian, CentOS,...).
Java: JRE hoặc JDK phiên bản 8 trở lên.
ClamAV: Đã cài đặt và cấu hình trên hệ thống.
Maven: Được sử dụng để xây dựng dự án.
Hướng dẫn sử dụng
1. Chạy ứng dụng
Đảm bảo đã cài đặt Java và ClamAV trên hệ thống.
Biên dịch và đóng gói dự án:
bash
Copy code
mvn clean package
Chạy ứng dụng:
bash
Copy code
java -jar target/clamav-project-1.0-SNAPSHOT.jar
2. Sử dụng giao diện
Nhấn nút Choose File/Directory để chọn tệp hoặc thư mục cần quét.
Nhấn nút Scan để bắt đầu quét.
Quan sát kết quả quét trong bảng và biểu đồ.
Nhấn Export to Excel để xuất kết quả ra tệp Excel trong thư mục result/.
3. Cấu hình ClamAV
Đảm bảo ClamAV đã được cài đặt trên hệ thống:
bash
Copy code
sudo apt update
sudo apt install clamav clamav-daemon
Cập nhật cơ sở dữ liệu ClamAV:
bash
Copy code
sudo freshclam
Cấu trúc dự án
plaintext
Copy code
clamav-project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── clamav/
│   │   │   │   ├── chart/
│   │   │   │   │   └── ChartGenerator.java
│   │   │   │   ├── export/
│   │   │   │   │   └── FileExporter.java
│   │   │   │   ├── processor/
│   │   │   │   │   └── ScanResultProcessor.java
│   │   │   │   ├── scanner/
│   │   │   │   │   └── ClamAVScanner.java
│   │   │   │   └── MainGUI.java
├── result/
│   └── (Thư mục chứa tệp Excel và ảnh kết quả)
├── pom.xml
└── README.md
