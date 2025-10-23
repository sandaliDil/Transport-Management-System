package com.vms.transportmanagementsystem.utill;

import com.vms.transportmanagementsystem.DTO.FuelPivotRow;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelExportUtil {

    public static void exportPivotToExcel(List<FuelPivotRow> data, List<LocalDate> dateList, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Fuel Report");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");

            // ✅ Create a centered cell style
            CellStyle centerStyle = workbook.createCellStyle();
            centerStyle.setAlignment(HorizontalAlignment.CENTER);

            // Header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Reg No");

            for (int i = 0; i < dateList.size(); i++) {
                String formattedDate = dateList.get(i).format(formatter);
                header.createCell(i + 1).setCellValue(formattedDate);
            }
            header.createCell(dateList.size() + 1).setCellValue("Total");

            // Data rows
            for (int i = 0; i < data.size(); i++) {
                FuelPivotRow rowData = data.get(i);
                Row row = sheet.createRow(i + 1);

                // Vehicle Reg No (not centered)
                row.createCell(0).setCellValue(rowData.getVehicleRegNum());

                // Quantities per date
                for (int j = 0; j < dateList.size(); j++) {
                    float qty = rowData.getQuantityForDate(dateList.get(j));
                    String formattedQty = formatFloatSmart(qty);

                    Cell qtyCell = row.createCell(j + 1);
                    qtyCell.setCellValue(formattedQty);
                    qtyCell.setCellStyle(centerStyle); // ✅ Center-align
                }

                // Total column
                float totalQty = rowData.getTotalQuantity();
                String formattedTotal = formatFloatSmart(totalQty);

                Cell totalCell = row.createCell(dateList.size() + 1);
                totalCell.setCellValue(formattedTotal);
                totalCell.setCellStyle(centerStyle); // ✅ Center-align
            }

            // Auto-size all columns
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

