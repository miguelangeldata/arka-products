package com.store.products.infraestructure.adapaters.out.reporter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;

import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.store.products.domain.models.Product;
import com.store.products.domain.ports.out.persistence.ReportExportPort;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component
public class ReporterAdapter implements ReportExportPort {

    private static final String FILE_PATH = "reports/";
    private static final Scheduler CB_SCHEDULER = Schedulers.boundedElastic();
    @Override
    public Mono<String> exportReport(List<Product> products, String format) {
        return Mono.fromCallable(() -> {
                    String formatUpper = format.toUpperCase();
                    String fileName = String.format("low_stock_report_%d.%s", System.currentTimeMillis(), formatUpper.toLowerCase());

                    java.io.File reportDir = new java.io.File(FILE_PATH);
                    if (!reportDir.exists()) {
                        reportDir.mkdirs();
                    }

                    if ("CSV".equals(formatUpper)) {
                        return exportToCsv(products, FILE_PATH + fileName);
                    } else if ("PDF".equals(formatUpper)) {
                        return exportToPdf(products, FILE_PATH + fileName);
                    } else {
                        throw new IllegalArgumentException("No Format Support: " + format);
                    }
                })
                .subscribeOn(CB_SCHEDULER);
    }
    private String exportToPdf(List<Product> products, String fullPath) throws IOException, DocumentException {

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fullPath));
            document.open();
            document.add(new Paragraph("Report Products For Supply"));
            document.add(new Paragraph("Generated : " + java.time.LocalDateTime.now()));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);

            table.addCell(new Phrase("ID"));
            table.addCell(new Phrase("Name"));
            table.addCell(new Phrase("Stock Available"));


            for (Product p : products) {
                table.addCell(String.valueOf(p.getId()));
                table.addCell(p.getName());
                table.addCell(String.valueOf(p.getAvailableStock()));
            }

            document.add(table);

            return "Pdf report was generated Successfully: " + fullPath;
        } catch (DocumentException e) {
            throw new RuntimeException("Fail", e);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }
    private String exportToCsv(List<Product> products, String fullPath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fullPath))) {
            writer.println("ID,SKU,NAME,AVAILABLE_STOCK");
            for (Product p : products) {
                writer.printf("%d,%s,%s,%d\n",
                        p.getId(),
                        p.getSku(),
                        p.getName(),
                        p.getAvailableStock()
                );
            }
            return "Report CSV was generated Successfully: " + fullPath;
        } catch (Exception e) {
            throw new RuntimeException("Fail to write the  CSV file :  " + e.getMessage(), e);
        }
    }
}
