package com.stockportfoliomanagementsystem;


import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;
public class EmailUtil {

    public static void sendInvoiceWithAttachment(String recipientEmail, String subject, String messageBody, File attachment) {
        final String senderEmail = "singhvart12345@gmail.com"; // use your email
        final String password = "uxud tlos zrmt vmul"; // use an App Password if using Gmail


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            // Body
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(messageBody);

            // Attachment
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(attachment);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email with attachment sent successfully!");

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}