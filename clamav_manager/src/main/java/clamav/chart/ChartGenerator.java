package clamav.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ChartGenerator {

    /**
     * Tạo biểu đồ và trả về JPanel để hiển thị trong giao diện Swing.
     *
     * @param totalFiles    Tổng số file đã quét
     * @param infectedFiles Số file bị nhiễm
     * @return JPanel chứa biểu đồ
     */
    public JPanel createChartPanel(int totalFiles, int infectedFiles) {
        // Tạo dữ liệu cho biểu đồ
        DefaultCategoryDataset dataset = createDataset(totalFiles, infectedFiles);

        // Tạo biểu đồ
        JFreeChart barChart = ChartFactory.createBarChart(
                "Scan Results",  // Tiêu đề biểu đồ
                "File Status",   // Trục X
                "Count",         // Trục Y
                dataset          // Dữ liệu
        );

        // Tùy chỉnh biểu đồ
        customizeChart(barChart);

        // Tạo JPanel chứa biểu đồ
        return new ChartPanel(barChart);
    }

    /**
     * Lưu biểu đồ dưới dạng file PNG.
     *
     * @param totalFiles    Tổng số file đã quét
     * @param infectedFiles Số file bị nhiễm
     * @param filePath      Đường dẫn lưu file PNG
     */
    public void generateBarChart(int totalFiles, int infectedFiles, String filePath) {
        // Tạo dữ liệu cho biểu đồ
        DefaultCategoryDataset dataset = createDataset(totalFiles, infectedFiles);

        // Tạo biểu đồ
        JFreeChart barChart = ChartFactory.createBarChart(
                "Scan Results",  // Tiêu đề biểu đồ
                "File Status",   // Trục X
                "Count",         // Trục Y
                dataset          // Dữ liệu
        );

        // Tùy chỉnh biểu đồ
        customizeChart(barChart);

        // Lưu biểu đồ dưới dạng file PNG
        try {
            ChartUtils.saveChartAsPNG(new File(filePath), barChart, 640, 480);
        } catch (Exception e) {
            System.err.println("Failed to save chart as PNG: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Tạo dữ liệu cho biểu đồ.
     *
     * @param totalFiles    Tổng số file đã quét
     * @param infectedFiles Số file bị nhiễm
     * @return DefaultCategoryDataset chứa dữ liệu
     */
    private DefaultCategoryDataset createDataset(int totalFiles, int infectedFiles) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int cleanFiles = totalFiles - infectedFiles;

        // In log để kiểm tra giá trị đầu vào
        System.out.println("Total Files: " + totalFiles);
        System.out.println("Infected Files: " + infectedFiles);
        System.out.println("Clean Files: " + cleanFiles);

        dataset.addValue(cleanFiles, "Clean Files", "Clean Files");
        dataset.addValue(infectedFiles, "Infected Files", "Infected Files");

        return dataset;
    }

    /**
     * Tùy chỉnh giao diện biểu đồ.
     *
     * @param chart Đối tượng JFreeChart cần tùy chỉnh
     */
    private void customizeChart(JFreeChart chart) {
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = new BarRenderer();

        // Đặt màu cho từng cột (theo chỉ số thứ tự)
        renderer.setSeriesPaint(0, Color.GREEN); // Cột "Clean Files" màu xanh lá cây
        renderer.setSeriesPaint(1, Color.RED);   // Cột "Infected Files" màu đỏ

        plot.setRenderer(renderer);
    }

    public void saveChartAsPNG(int totalFiles, int infectedFiles, String filePath) {
        DefaultCategoryDataset dataset = createDataset(totalFiles, infectedFiles);

        JFreeChart barChart = ChartFactory.createBarChart(
                "Scan Results", 
                "File Status", 
                "Count", 
                dataset
        );

        customizeChart(barChart);

        try {
            ChartUtils.saveChartAsPNG(new File(filePath), barChart, 640, 480);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
