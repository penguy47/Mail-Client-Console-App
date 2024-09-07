package src;
import java.io.IOException;

import src.connection.POP3Client;
import src.connection.SMTPClient;
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

            System.out.println("[Controller]: SMTP, POP3 connection test successful.");
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
}
