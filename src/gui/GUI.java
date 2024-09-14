package src.gui;

import javax.swing.*;
import src.EmailController;
import java.awt.*;

public class GUI {
    JFrame window;
    EmailController emailController;

    JLabel userLabel;

    JTabbedPane tabbedPane;

    Font textFont;


    public GUI(EmailController emailController){
        this.emailController = emailController;
        textFont = new Font("Arial", Font.PLAIN, 18);
    
        createWindow();
        createUserLabel();
        createTabbedPane();

        window.setVisible(true);
    }

    private void createWindow(){
        window = new JFrame("Mail client");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800,600);
        window.setLayout(null);
    }

    private void createUserLabel(){
        userLabel = new JLabel("Hello, "
        + emailController.user.username
        + " (" + emailController.user.email + ")");
        userLabel.setBounds(0,0,800,30);
        userLabel.setHorizontalAlignment(JLabel.CENTER);
        userLabel.setFont(textFont);
        
        window.add(userLabel);
    }

    private void createTabbedPane(){
        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(10,30,750,525);

        tabbedPane.addTab("Mail Box", new MailBoxPanel(emailController, textFont));
        tabbedPane.addTab("Send Mail", new NewMailPanel(emailController, textFont));

        window.add(tabbedPane);
    }

}