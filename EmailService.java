import java.io.IOException;

import connection.POP3Client;
import connection.SMTPClient;

public class EmailService {
    public EmailService(String smtpHost, int smtpPort, String pop3Host, int pop3Port){
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

}
