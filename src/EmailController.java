package src;

import src.entity.Mail;

import java.io.File;
import java.io.IOException;

import src.connection.POP3Client;
import src.entity.Attachment;

public class EmailController {
    EmailService emailService;
    EmailController(String smtpHost, int smtpPort, String pop3Host, int pop3Port){
        emailService = new EmailService(smtpHost, smtpPort, pop3Host, pop3Port);

        // Mail result = emailService.retrieveMail("nigga.com","bruh", 1);
        // System.out.println(result.toString());

        // emailService.downloadAttachments("nigga.com", "bruh", 3,"E:\\Projects");

        // Mail mail = new Mail();
        // mail.setFrom("lan47.com");
        // mail.addTo("nigga.com");
        // mail.setSubject("Test retrieve data");
        // mail.setBody("Never is good\nIm insane");
        // mail.addAttachment(new Attachment(new File("background.png")));
        // mail.addAttachment(new Attachment(new File("lightItem.txt")));

        // emailService.sendEmail(mail);
    }

    
}
