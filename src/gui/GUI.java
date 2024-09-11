package src.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import src.EmailController;
import src.entity.Folder;
import src.entity.Mail;
import src.entity.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.*;

public class GUI {
    JFrame window;
    EmailController emailController;

    JLabel userLabel;

    JTabbedPane tabbedPane;
    JPanel mailBoxPanel;
    JPanel newMailPanel;
    JPanel filterPanel;

    JList<String> folderList;
    JList<String> mailList;
    private Map<String, Mail> mailMap = new HashMap<>();

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

        createMailBoxPanel();
        createNewMailPanel();
        createFilterPanel();

        tabbedPane.addTab("Mail Box", mailBoxPanel);
        tabbedPane.addTab("Send Mail", newMailPanel);
        tabbedPane.addTab("Filters", filterPanel);

        window.add(tabbedPane);
    }

    private void createMailBoxPanel() {
        mailBoxPanel = new JPanel();
        mailBoxPanel.setBounds(0, 0, 100, 200);
        mailBoxPanel.setFocusable(false);
        mailBoxPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
    
        createFolderList();
        
        mailBoxPanel.add(folderList);
        mailBoxPanel.add(mailList);
    }
    
    private void createFolderList(){
        String[] folderNames = emailController.folders
            .stream()
            .map(Folder::getName)
            .toArray(String[]::new);
    
        folderList = new JList<>(folderNames);
        folderList.setFont(textFont);
        folderList.setPreferredSize(new Dimension(100, 450));
        folderList.setBorder(BorderFactory.createLineBorder(Color.black));
    
        mailList = new JList<>(new String[] {});
        mailList.setFont(textFont);
        mailList.setPreferredSize(new Dimension(450, 450));
        mailList.setBorder(BorderFactory.createLineBorder(Color.black));
    
        folderList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                createMailList();
            }
        });

        folderList.setSelectedIndex(0);
    }

    private void createMailList(){
        String folderName = folderList.getSelectedValue();
        List<Mail> mails = null;

        for (Folder folder : emailController.folders) {
            if (folder.getName().equals(folderName)) {
                mails = folder.getMails();
                break;
            }
        }

        if (mails == null) {
            mails = Collections.emptyList();
        }

        String[] mailNames = mails
                            .stream()
                            .map(mail -> {
                                String displayText = mail.getSubject() + " - From: " + mail.getFrom();
                                mailMap.put(displayText, mail);
                                return displayText;
                            })
                            .toArray(String[]::new);

        mailBoxPanel.remove(mailList);
        mailList = new JList<>(mailNames);
        mailList.setFont(textFont);
        mailList.setPreferredSize(new Dimension(600, 450));
        mailList.setBorder(BorderFactory.createLineBorder(Color.black));

        mailList.addListSelectionListener((ee) -> {
            if (!ee.getValueIsAdjusting()) {
                String selectedMailText = mailList.getSelectedValue();
                Mail selectedMail = mailMap.get(selectedMailText);
                if (selectedMail != null) {
                    System.out.println("Selected Mail: " + selectedMail.getSubject());
                    //
                    //
                    //
                }
            }
        });

        mailBoxPanel.add(mailList);

        mailBoxPanel.revalidate();
        mailBoxPanel.repaint();
    }

    private void createNewMailPanel(){
        newMailPanel = new JPanel();
        newMailPanel.setBounds(0,0,100,200);
        mailBoxPanel.setFocusable(false);
    }

    private void createFilterPanel(){
        filterPanel = new JPanel();
        filterPanel.setBounds(0,0,100,200);
        filterPanel.setFocusable(false);
    }
}