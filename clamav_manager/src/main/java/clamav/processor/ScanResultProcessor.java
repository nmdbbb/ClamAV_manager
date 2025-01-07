package clamav.processor;

import java.util.HashMap;
import java.util.Map;

public class ScanResultProcessor {
    /**
     * Phân tích kết quả quét và trả về Map chứa tên file, loại virus, và đường dẫn đầy đủ.
     * @param scanOutput Kết quả quét của ClamAV.
     * @return Map chứa file bị nhiễm (key: tên file, value: mảng gồm [loại virus, đường dẫn đầy đủ]).
     */
    public Map<String, String[]> parseInfectedFiles(String scanOutput) {
        Map<String, String[]> infectedFiles = new HashMap<>();
        String[] lines = scanOutput.split("\n");
        for (String line : lines) {
            if (line.contains("FOUND")) {
                // Dòng có dạng: /path/to/file: VirusType FOUND
                String[] parts = line.split(":");
                String fullPath = parts[0].trim();
                String fileName = fullPath.substring(fullPath.lastIndexOf("/") + 1); // Lấy tên file
                String virusType = parts[1].replace("FOUND", "").trim();
                infectedFiles.put(fileName, new String[]{virusType, fullPath});
            }
        }
        return infectedFiles;
    }
}
