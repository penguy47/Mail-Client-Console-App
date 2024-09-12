package src.gui;

import javax.swing.*;
import src.EmailController;
import java.awt.*;

public class GUI {
    JFrame window;
    EmailController emailController;

    JLabel userLabel;

    JTabbedPane tabbedPane;
    JPanel newMailPanel;
    JPanel filterPanel;

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

        createNewMailPanel();
        createFilterPanel();

        tabbedPane.addTab("Mail Box", new MailBoxPanel(textFont, emailController));
        tabbedPane.addTab("Send Mail", newMailPanel);
        tabbedPane.addTab("Filters", filterPanel);

        window.add(tabbedPane);
    }

    private void createNewMailPanel(){
        newMailPanel = new JPanel();
        newMailPanel.setBounds(0,0,100,200);
        newMailPanel.setFocusable(false);
    }

    private void createFilterPanel(){
        filterPanel = new JPanel();
        filterPanel.setBounds(0,0,100,200);
        filterPanel.setFocusable(false);
    }

}