package src;
import src.config.*;
public class Main {
    public static void main(String[] args) {
        new EmailController(XMLConfigReader.readConfig("src\\config.xml"));
        
    }
}
