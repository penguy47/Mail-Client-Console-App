package src;
import src.config.*;
public class Main {
    public static void main(String[] args) {
        // EmailController controller = new EmailController(
        //     "127.0.0.1", 2225,
        //      "127.0.0.1", 3335
        // );

        Config a = XMLConfigReader.readConfig("src\\config.xml");

        
    }
}
