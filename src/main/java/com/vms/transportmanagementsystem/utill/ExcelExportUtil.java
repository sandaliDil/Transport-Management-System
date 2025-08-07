package com.vms.transportmanagementsystem.utill;

import com.vms.transportmanagementsystem.DTO.FuelPivotRow;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelExportUtil {
//    public static void exportPivotToExcel(List<FuelPivotRow> data, List<LocalDate> dateList, String filePath) {
//        try (Workbook workbook = new XSSFWorkbook()) {
//            Sheet sheet = workbook.createSheet("Fuel Report");
//
//            // Header row
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");
//
//            Row header = sheet.createRow(0);
//            header.createCell(0).setCellValue("Reg No");
//
//            for (int i = 0; i < dateList.size(); i++) {
//                String formattedDate = dateList.get(i).format(formatter);
//                header.createCell(i + 1).setCellValue(formattedDate);
//            }
//
//            // Data rows
//            for (int i = 0; i < data.size(); i++) {
//                FuelPivotRow rowData = data.get(i);
//                Row row = sheet.createRow(i + 1);
//                row.createCell(0).setCellValue(rowData.getVehicleRegNum());
//
//                for (int j = 0; j < dateList.size(); j++) {
//                    float qty = rowData.getQuantityForDate(dateList.get(j));
//                    String formattedQty = formatFloatSmart(qty);
//                    row.createCell(j + 1).setCellValue(formattedQty);
//                }
//            }
//
//            // Autosize columns
//            for (int i = 0; i <= dateList.size(); i++) {
//                sheet.autoSizeColumn(i);
//            }
//
//            // Save to file
//            try (FileOutputStream out = new FileOutputStream(filePath)) {
//                workbook.write(out);
//            }
//
//            System.out.println("✅ Excel file saved to: " + filePath);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Helper to format float to string without unnecessary trailing zeros
//    private static String formatFloatSmart(float value) {
//        if (value == (long) value) {
//            return String.format("%d", (long) value); // No decimals
//        } else {
//            return String.format("%.3f", value).replaceAll("0*$", "").replaceAll("\\.$", "");
//        }
//    }

    public static void exportPivotToExcel(List<FuelPivotRow> data, List<LocalDate> dateList, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Fuel Report");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");

            // Header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Reg No");

            // Date columns headers
            for (int i = 0; i < dateList.size(); i++) {
                String formattedDate = dateList.get(i).format(formatter);
                header.createCell(i + 1).setCellValue(formattedDate);
            }
            // Add "Total" header at the end
            header.createCell(dateList.size() + 1).setCellValue("Total");

            // Data rows
            for (int i = 0; i < data.size(); i++) {
                FuelPivotRow rowData = data.get(i);
                Row row = sheet.createRow(i + 1);

                row.createCell(0).setCellValue(rowData.getVehicleRegNum());

                // Quantities for each date
                for (int j = 0; j < dateList.size(); j++) {
                    float qty = rowData.getQuantityForDate(dateList.get(j));
                    String formattedQty = formatFloatSmart(qty);
                    row.createCell(j + 1).setCellValue(formattedQty);
                }

                // Total quantity in last column
                float totalQty = rowData.getTotalQuantity();
                String formattedTotal = formatFloatSmart(totalQty);
                row.createCell(dateList.size() + 1).setCellValue(formattedTotal);
            }

            // Autosize all columns including the Total column
            for (int i = 0; i <= dateList.size() + 1; i++) {
                sheet.autoSizeColumn(i);
            }

            // Save to file
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                workbook.write(out);
            }

            System.out.println("✅ Excel file saved to: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String formatFloatSmart(float value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            return String.format("%.3f", value).replaceAll("0*$", "").replaceAll("\\.$", "");
        }
    }



}
