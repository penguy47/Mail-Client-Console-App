import java.io.IOException;

import connection.POP3Client;
import connection.SMTPClient;

public class EmailController {
    private POP3Client pop3Client;
    private SMTPClient smtpClient;
    EmailController(String smtpHost, int smtpPort, String pop3Host, int pop3Port){
        try {
            smtpClient = new SMTPClient(smtpHost, smtpPort);
            pop3Client = new POP3Client(pop3Host, pop3Port);
        } catch (IOException e) {
            System.out.println("[Controller]: Cannot connect to protocols");
        }
    }
}
