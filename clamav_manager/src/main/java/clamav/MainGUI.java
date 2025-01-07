package clamav;

import clamav.chart.ChartGenerator;
import clamav.export.FileExporter;
import clamav.scanner.ClamAVScanner;
import clamav.processor.ScanResultProcessor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainGUI {
    private JFrame frame;
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private JPanel chartPanel;
    private JProgressBar progressBar;
    private JLabel currentFileLabel;

    public MainGUI() {
        initialize();
    }

    /**
     * Khởi tạo giao diện chính của ứng dụng.
     */
    private void initialize() {
        frame = new JFrame("ClamAV Scanner");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tạo panel chính chứa các thành phần giao diện
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Phần giao diện chọn file hoặc thư mục
        JPanel filePanel = new JPanel();
        JButton chooseFileButton = new JButton("Choose File/Directory");
        JTextField filePathField = new JTextField(40);
        filePathField.setEditable(false);
        JButton scanButton = new JButton("Scan");

        filePanel.add(chooseFileButton);
        filePanel.add(filePathField);
        filePanel.add(scanButton);

        // Thanh tiến trình và nhãn hiển thị trạng thái
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        currentFileLabel = new JLabel("Ready to scan...");
        currentFileLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Bảng hiển thị kết quả quét
        tableModel = new DefaultTableModel(new String[]{"File Name", "Virus Type", "File Path"}, 0);
        resultsTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(resultsTable);

        // Panel biểu đồ
        chartPanel = new JPanel();
        chartPanel.setPreferredSize(new Dimension(400, 400));
        chartPanel.setBorder(BorderFactory.createTitledBorder("Scan Chart"));

        // Nút xuất kết quả ra Excel
        JButton exportButton = new JButton("Export to Excel");

        // Panel chứa thanh tiến trình và nút xuất file
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(progressBar, BorderLayout.NORTH);
        bottomPanel.add(currentFileLabel, BorderLayout.CENTER);
        bottomPanel.add(exportButton, BorderLayout.SOUTH);

        // Thêm các thành phần vào panel chính
        mainPanel.add(filePanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(chartPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);

        // Xử lý sự kiện chọn file hoặc thư mục
        chooseFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filePathField.setText(selectedFile.getAbsolutePath());
            }
        });

        // Xử lý sự kiện quét file hoặc thư mục
        scanButton.addActionListener(e -> {
            String filePath = filePathField.getText();
            if (filePath.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please select a file or directory to scan.");
                return;
            }

            new Thread(() -> {
                try {
                    // Quét thư mục hoặc file
                    ClamAVScanner scanner = new ClamAVScanner();
                    String scanOutput = scanner.scanDirectory(filePath);

                    // Phân tích kết quả quét
                    ScanResultProcessor processor = new ScanResultProcessor();
                    Map<String, String[]> infectedFiles = processor.parseInfectedFiles(scanOutput);

                    // Tính toán số lượng file và hiển thị kết quả
                    File dir = new File(filePath);
                    int totalFiles = countFilesRecursively(dir);
                    int infectedFilesCount = infectedFiles.size();
                    int cleanFilesCount = totalFiles - infectedFilesCount;

                    SwingUtilities.invokeLater(() -> {
                        updateTable(infectedFiles, filePath); // Cập nhật bảng kết quả
                        updateChart(totalFiles, cleanFilesCount, infectedFilesCount); // Cập nhật biểu đồ
                        progressBar.setValue(100);
                        currentFileLabel.setText("Scan complete!");

                        // Hiển thị thông báo hoàn thành
                        JOptionPane.showMessageDialog(frame, "Scan complete!");

                        // Xuất biểu đồ dưới dạng file PNG
                        String chartExportPath = new File("result/scan_chart.png").getAbsolutePath();
                        ChartGenerator chartGenerator = new ChartGenerator();
                        chartGenerator.generateBarChart(totalFiles, infectedFilesCount, chartExportPath);
                        JOptionPane.showMessageDialog(frame, "Chart exported successfully to " + chartExportPath);
                        // Reset thanh tiến trình và trạng thái
                        resetProgressBar();
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage()));
                }
            }).start();
        });

        // Xử lý sự kiện xuất kết quả ra file Excel
        exportButton.addActionListener(e -> {
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(frame, "No scan results to export!");
                return;
            }

            // Kiểm tra và tạo thư mục "result" nếu chưa tồn tại
            File resultDir = new File("result");
            if (!resultDir.exists()) {
                resultDir.mkdirs();
            }

            // Đường dẫn lưu file Excel
            String exportPath = new File("result/scan_results.xlsx").getAbsolutePath();

            try {
                FileExporter exporter = new FileExporter();
                Map<String, String[]> dataMap = new HashMap<>();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String[] row = new String[tableModel.getColumnCount()];
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        row[j] = tableModel.getValueAt(i, j).toString();
                    }
                    dataMap.put("Row " + (i + 1), row);
                }

                // Xuất file Excel
                exporter.exportToExcel(dataMap, exportPath);
                JOptionPane.showMessageDialog(frame, "Results exported successfully to " + exportPath);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Failed to export results: " + ex.getMessage());
            }


        });
        frame.setVisible(true);
    }

    /**
     * Đếm tất cả file trong thư mục và các thư mục con.
     */
    private int countFilesRecursively(File dir) {
        File[] files = dir.listFiles();
        if (files == null) return 0;

        int count = 0;
        for (File file : files) {
            if (file.isDirectory()) {
                count += countFilesRecursively(file);
            } else {
                count++;
            }
        }
        return count;
    }

    /**
     * Cập nhật bảng kết quả với danh sách file bị nhiễm.
     */
    private void updateTable(Map<String, String[]> infectedFiles, String filePath) {
        tableModel.setRowCount(0);
        for (Map.Entry<String, String[]> entry : infectedFiles.entrySet()) {
            String fileName = entry.getKey();
            String[] details = entry.getValue();
            tableModel.addRow(new Object[]{fileName, details[0], filePath});
        }
    }

    /**
     * Cập nhật biểu đồ hiển thị số file sạch và file bị nhiễm.
     */
    private void updateChart(int totalFiles, int cleanFiles, int infectedFiles) {
        ChartGenerator chartGenerator = new ChartGenerator();
        JPanel chart = chartGenerator.createChartPanel(totalFiles, infectedFiles);

        chartPanel.removeAll();
        chartPanel.setLayout(new BorderLayout());
        chartPanel.add(chart, BorderLayout.CENTER);

        chartPanel.revalidate();
        chartPanel.repaint();
    }

    /**
     * Reset thanh tiến trình và trạng thái hiển thị.
     */
    private void resetProgressBar() {
        progressBar.setValue(0);
        progressBar.setStringPainted(false);
        currentFileLabel.setText("Ready to scan...");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
