package com.example.paymentservice.util;

import com.example.paymentservice.entity.Payment_Entity;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.hibernate.Transaction;

import java.io.ByteArrayOutputStream;

public class ReceiptGenerator {

    public static byte[] generate(Payment_Entity tx) {
        try {
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font fontBody = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("Payment Receipt", fontTitle));
            document.add(new Paragraph("--------------------------------------------------"));

            document.add(new Paragraph("Transaction ID: " + tx.getPaymentId(), fontBody));
            document.add(new Paragraph("User ID       : " + tx.getUserId(), fontBody));
            document.add(new Paragraph("Reservation ID: " + tx.getReservationId(), fontBody));
            document.add(new Paragraph("Amount (LKR) : " + tx.getAmount(), fontBody));
            document.add(new Paragraph("Method        : " + tx.getPaymentMethod(), fontBody));


            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }
}