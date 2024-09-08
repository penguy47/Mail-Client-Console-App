package src;

import src.entity.Mail;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import src.config.Config;
import src.connection.POP3Client;
import src.entity.Attachment;
import src.entity.Folder;

public class EmailController {
    EmailService emailService;
    Config config;
    List<Folder> folders;
    
    EmailController(Config config){
        this.config = config;
        createEmailService();
        createFolders();

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

    private void createEmailService(){
        emailService = new EmailService(
            config.getServerConfig().getSmtpHost(),
            config.getServerConfig().getSmtpPort(),
            config.getServerConfig().getPop3Host(),
            config.getServerConfig().getPop3Port()
        );
    }

    private void createFolders(){
        folders = new ArrayList<>();
        // Default folders
        folders.add(new Folder("Inbox"));
        folders.add(new Folder("Project"));
        folders.add(new Folder("Important"));
        folders.add(new Folder("Work"));
        folders.add(new Folder("Spam"));
    }

    

}
