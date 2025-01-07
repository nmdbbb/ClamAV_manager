package clamav.export;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class FileExporter {
    /**
     * Xuất kết quả quét ra file Excel.
     * @param infectedFiles Map chứa file bị nhiễm (key: tên file, value: mảng gồm [loại virus, đường dẫn đầy đủ]).
     * @param filePath Đường dẫn nơi lưu file Excel.
     */
    public void exportToExcel(Map<String, String[]> infectedFiles, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Scan Results");

        // Tạo tiêu đề cho bảng
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("File Name");
        headerRow.createCell(1).setCellValue("Virus Type");
        headerRow.createCell(2).setCellValue("File Path");

        // Ghi dữ liệu
        int rowNum = 1;
        for (Map.Entry<String, String[]> entry : infectedFiles.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entry.getKey()); // Tên file
            row.createCell(1).setCellValue(entry.getValue()[0]); // Loại virus
            row.createCell(2).setCellValue(entry.getValue()[1]); // Đường dẫn đầy đủ
        }

        // Lưu file Excel
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
