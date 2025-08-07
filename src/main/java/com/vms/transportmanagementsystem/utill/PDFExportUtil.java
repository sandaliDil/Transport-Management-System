package com.vms.transportmanagementsystem.utill;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.vms.transportmanagementsystem.DTO.FuelPivotRow;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

public class PDFExportUtil {
    public static void exportPivotToPDF(List<FuelPivotRow> data, List<LocalDate> dateList, String filePath) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph title = new Paragraph("Fuel Pivot Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Table
            PdfPTable table = new PdfPTable(dateList.size() + 1);
            table.setWidthPercentage(100);

            // Header row
            table.addCell("Vehicle Reg No");
            for (LocalDate date : dateList) {
                table.addCell(date.toString());
            }

            // Data rows
            for (FuelPivotRow row : data) {
                table.addCell(row.getVehicleRegNum());
                for (LocalDate date : dateList) {
                    table.addCell(String.valueOf(row.getQuantityForDate(date)));
                }
            }

            document.add(table);
            document.close();

            System.out.println("✅ PDF file saved to: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
