
public class EmailController {
    EmailService emailService;
    EmailController(String smtpHost, int smtpPort, String pop3Host, int pop3Port){
        emailService = new EmailService(smtpHost, smtpPort, pop3Host, pop3Port);
    }

    
}
