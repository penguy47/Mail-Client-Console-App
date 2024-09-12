package src.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

import src.EmailController;
import src.entity.Attachment;
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

    JList<Folder> folderList;
    JList<Mail> mailList;
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
        Folder[] folders = emailController.folders.toArray(new Folder[0]);
        folderList = new JList<>(folders);
        folderList.setFont(textFont);
        folderList.setPreferredSize(new Dimension(100, 450));
        folderList.setBorder(BorderFactory.createLineBorder(Color.black));
    
        mailList = new JList<>(new Mail[0]);
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
        Folder selectedFolder = folderList.getSelectedValue();
        List<Mail> mails = selectedFolder.getMails();

        if (mails == null) {
            mails = Collections.emptyList();
        }

        mailBoxPanel.remove(mailList);
        mailList = new JList<>(mails.toArray(new Mail[0]));
        mailList.setFont(textFont);
        mailList.setPreferredSize(new Dimension(600, 450));
        mailList.setBorder(BorderFactory.createLineBorder(Color.black));

        mailList.addListSelectionListener((ee) -> {
            if (!ee.getValueIsAdjusting()) {
                Mail selectedMail = mailList.getSelectedValue();
                mailDisplayWindow(selectedMail);
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

    private void mailDisplayWindow(Mail mail){
        JFrame mailWindow = new JFrame();
        mailWindow.setSize(600,620);
        mailWindow.setTitle(mail.getSubject());
        mailWindow.setLayout(null);

        JTextArea textArea = new JTextArea();
        
        textArea.setEditable(false);
        textArea.setFont(textFont);
        textArea.setText(mail.toFullMailString());
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setCaret(new DefaultCaret() {
            @Override
            public void paint(Graphics g) {
                //do nothing
            }
        });
    
        //
        String[] attachNames = mail.getAttachments()
                            .stream()
                            .map(Attachment::getName)
                            .toArray(String[]::new);
        JList<String> attachList = new JList<>(attachNames);
        attachList.setFont(textFont);
        attachList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        //

        JScrollPane scrollPaneBody = new JScrollPane(textArea);
        JScrollPane scrollPaneFooter = new JScrollPane(attachList);
        scrollPaneBody.setBounds(10,10,550,450);
        scrollPaneFooter.setBounds(10,480,200,90);

        JRadioButton saveAllrb = new JRadioButton("Download all files");
        JRadioButton saveSomerb = new JRadioButton("Download specific files");
        JLabel noteSaveLabel = new JLabel("Hold ctrl and click to choose more");

        saveAllrb.setFont(textFont);
        saveSomerb.setFont(textFont);

        ButtonGroup group = new ButtonGroup();
        group.add(saveAllrb);
        group.add(saveSomerb);

        saveAllrb.setBounds(220, 480, 230,30);
        saveSomerb.setBounds(220, 510, 230,30);
        noteSaveLabel.setBounds(250, 540, 230, 30);

        JButton downloadb = new JButton("Download");

        downloadb.setBounds(460, 480, 100,30);

        // First render
        saveAllrb.setSelected(true);
        if(attachNames.length == 0) {
            saveAllrb.setEnabled(false);
            saveSomerb.setEnabled(false);
            downloadb.setEnabled(false);
        }



        mailWindow.add(saveAllrb);
        mailWindow.add(saveSomerb);
        mailWindow.add(noteSaveLabel);
        mailWindow.add(downloadb);

        mailWindow.add(scrollPaneBody);
        mailWindow.add(scrollPaneFooter);
        mailWindow.setVisible(true);
    }
}