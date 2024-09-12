package src.gui;

import javax.swing.*;
import java.awt.*;

import src.EmailController;
import src.entity.Folder;
import src.entity.Mail;
import java.util.Collections;
import java.util.List;


public class MailBoxPanel extends JPanel {

    JList<Folder> folderList;
    JList<Mail> mailList;
    EmailController emailController;
    Font textFont;

    public MailBoxPanel(Font texFont, EmailController emailController){
        this.textFont = texFont;
        this.emailController = emailController;
        this.setBounds(0, 0, 100, 200);
        this.setFocusable(false);
        this.setLayout(new FlowLayout(FlowLayout.LEADING));

        createFolderList();
        createMailList();

        this.add(folderList);
        this.add(mailList);

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

        this.remove(mailList);
        mailList = new JList<>(mails.toArray(new Mail[0]));
        mailList.setFont(textFont);
        mailList.setPreferredSize(new Dimension(600, 450));
        mailList.setBorder(BorderFactory.createLineBorder(Color.black));

        mailList.addListSelectionListener((ee) -> {
            if (!ee.getValueIsAdjusting()) {
                Mail selectedMail = mailList.getSelectedValue();
                new MailDisplayFrame(emailController, textFont, selectedMail,mailList.getSelectedIndex()+1);
            }
        });

        this.add(mailList);

        this.revalidate();
        this.repaint();
    }

}
