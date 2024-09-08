package src;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import src.connection.POP3Client;
import src.connection.SMTPClient;
import src.entity.Attachment;
import src.entity.Mail;

public class EmailService {
    private String smtpHost;
    private String pop3Host;
    private int smtpPort;
    private int pop3Port;

    public EmailService(String smtpHost, int smtpPort, String pop3Host, int pop3Port){
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.pop3Host = pop3Host;
        this.pop3Port = pop3Port;

        try {
            System.out.println("[Controller]: SMTP, POP3 connection testing...");

            SMTPClient smtpClient = new SMTPClient(smtpHost, smtpPort);
            POP3Client pop3Client = new POP3Client(pop3Host, pop3Port);

            smtpClient.disconnect();
            pop3Client.disconnect();

            System.out.println("[Controller]: SMTP, POP3 is ready to use!");
        } catch (IOException e) {
            System.out.println("[Controller]: Cannot connect to protocols");
        }
    }

    public void sendEmail(Mail mail){
        try {
            SMTPClient smtpClient = new SMTPClient(smtpHost, smtpPort);

            smtpClient.connect();

            smtpClient.sendCommand("MAIL FROM:<" + mail.getFrom() + ">");

            for(String recipient : mail.getTo()){
                smtpClient.sendCommand("RCPT TO:<"+recipient+">");
            }

            for(String recipient : mail.getCc()){
                smtpClient.sendCommand("RCPT TO:<"+recipient+">");
            }

            for(String recipient : mail.getBcc()){
                smtpClient.sendCommand("RCPT TO:<"+recipient+">");
            }

            smtpClient.sendCommand("DATA");

            smtpClient.sendLine(mail.getHeaderString());

            for(String line : mail.getBodyLines()) {
                smtpClient.sendLine(line);
            }

            smtpClient.sendCommand(".");

            smtpClient.disconnect();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Mail retrieveMail(String username, String encodedPassword, int index) { // this server has no auth
        Mail mail = new Mail();
        boolean bBody = false;
        try {
            POP3Client pop3Client = new POP3Client(pop3Host, pop3Port);
            pop3Client.sendCommand("USER "+username);
            pop3Client.sendCommand("PASS " + encodedPassword);
            pop3Client.sendCommand("RETR "+index);

            String line = null;
            while((line = pop3Client.nextLine())!="."){
                if(line.startsWith("From: ")){
                    mail.setFrom(line.substring(6).trim());
                } else if(line.startsWith("To: ")){
                    String toEmails = line.substring(4).trim();
                    String[] emails = toEmails.split(",");
                    for(String email : emails) mail.addTo(email.trim());
                } else if(line.startsWith("Cc: ")){
                    String ccEmails = line.substring(4).trim();
                    String[] emails = ccEmails.split(",");
                    for(String email : emails) mail.addCc(email.trim());
                } else if(line.startsWith("Bcc: ")){
                    String bccEmails = line.substring(5).trim();
                    String[] emails = bccEmails.split(",");
                    for(String email : emails) mail.addBcc(email.trim());
                } else if(line.startsWith("Subject: ")) {
                    mail.setSubject(line.substring(8).trim());
                } else if(line.startsWith("--boundary--")){
                    break;
                } else if(line.startsWith("--boundary_attachment")) {
                    bBody = false;
                    pop3Client.nextLine(); // ignore
                    pop3Client.nextLine(); // ignore
                    line = pop3Client.nextLine();
                    mail.addAttachment(new Attachment(line.substring(
                        line.indexOf("filename=") + 9
                    ).replace("\"", "")
                    .trim()
                    ));
                } else if(line.startsWith("--boundary")){
                    bBody = true;
                } else if(bBody){
                    if(mail.getBody()==null) mail.setBody(line);
                    else mail.setBody((mail.getBody() + "\n" + line).trim());
                }
            }
            pop3Client.disconnect();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return mail;
    }

    public void downloadAttachments(String username, String encodedPassword, int index, String desPath){
        try {
            String line;
            POP3Client pop3Client = new POP3Client(pop3Host, pop3Port);
            pop3Client.sendCommand("USER "+username);
            pop3Client.sendCommand("PASS " + encodedPassword);
            pop3Client.sendCommand("RETR "+index);

            while((line = pop3Client.nextLine())!="."){
                if(line.startsWith("--boundary--")){
                    break;
                } else if(line.startsWith("--boundary_attachment")) {
                    pop3Client.nextLine(); // ignore
                    pop3Client.nextLine(); // ignore
                    line = pop3Client.nextLine();
                    String filename = line.substring(line.indexOf("filename=") + 9)
                                        .replace("\"", "")
                                        .trim();
                    pop3Client.nextLine(); // ignore
                    String encodedString = "";
                    while((line=pop3Client.nextLine())!=""){
                        if(line.length()==0) break;
                        encodedString += line;
                    }
                    byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
                    File file = new File(desPath + File.separator + filename);
                    file.getParentFile().mkdirs();

                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        fos.write(decodedBytes);
                        fos.flush();
                    };
                } 
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
