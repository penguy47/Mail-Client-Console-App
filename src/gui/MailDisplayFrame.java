package src.gui;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import src.EmailController;
import src.entity.Attachment;
import src.entity.Mail;

import java.awt.*;

public class MailDisplayFrame extends JFrame {

    EmailController emailController;
    
    Font textFont;
    Mail mail;
    int indexMail;

    JTextArea textArea;
    JList<Attachment> attachList;

    JScrollPane scrollPaneBody, scrollPaneFooter;

    JRadioButton saveAllrb, saveSomerb;
    JButton downloadb;

    public MailDisplayFrame(EmailController emailController, Font texFont, Mail mail, int indexMail){
        this.emailController = emailController;
        this.textFont = texFont;
        this.mail = mail;
        this.indexMail = indexMail;

        this.setSize(600,620);
        this.setTitle(mail.getSubject());
        this.setLayout(null);

        createTextArea();
        createAttachList();
        createButtons();
        createScrollPanes();
        this.setVisible(true);
    }

    private void createTextArea(){
        textArea = new JTextArea();
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
    }

    private void createAttachList(){
        attachList = new JList<>(mail.getAttachments().toArray(new Attachment[0]));
        attachList.setFont(textFont);
        attachList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    private void createScrollPanes(){
        scrollPaneBody = new JScrollPane(textArea);
        scrollPaneBody.setBounds(10,10,550,450);

        scrollPaneFooter = new JScrollPane(attachList);
        scrollPaneFooter.setBounds(10,480,200,90);

        this.add(scrollPaneBody);
        this.add(scrollPaneFooter);
    }

    private void createButtons(){
        saveAllrb = new JRadioButton("Download all files");
        saveSomerb = new JRadioButton("Download specific files");
        ButtonGroup group = new ButtonGroup();
        group.add(saveAllrb);
        group.add(saveSomerb);
        saveAllrb.setBounds(220, 480, 230,30);
        saveSomerb.setBounds(220, 510, 230,30);

        downloadb = new JButton("Download");
        downloadb.setFocusable(false);
        downloadb.setBounds(460, 480, 100,30);

        saveAllrb.addActionListener((e) -> {
            int itemCount = attachList.getModel().getSize();
                
            int[] indices = new int[itemCount];
            for (int i = 0; i < itemCount; i++) {
                indices[i] = i;
            }
            attachList.setSelectedIndices(indices);
        });

        saveSomerb.addActionListener((e) -> {
            attachList.setSelectedIndices(new int[0]);
        });

        downloadb.addActionListener((e) -> {
            String desPath = "E:\\Trash";
            if(saveAllrb.isSelected()
            || (saveSomerb.isSelected() && attachList.getSelectedIndices().length != 0)) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = fileChooser.showOpenDialog(null);
                if(returnValue == JFileChooser.APPROVE_OPTION){
                    desPath = fileChooser.getSelectedFile().toPath().toString();
                }
            }

            if(saveAllrb.isSelected()){
                emailController.downloadFiles(indexMail, null, desPath);
                JOptionPane.showMessageDialog(null, "Downloaded in "+desPath, "Info", JOptionPane.INFORMATION_MESSAGE);
            } else if(saveSomerb.isSelected()){
                if(attachList.getSelectedIndices().length == 0){
                    JOptionPane.showMessageDialog(null, "Please select files", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    emailController.downloadFiles(indexMail, attachList.getSelectedIndices(), desPath);
                    JOptionPane.showMessageDialog(null, "Downloaded in "+desPath, "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select modes", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });

        if(mail.getAttachments().size() == 0) {
            saveAllrb.setEnabled(false);
            saveSomerb.setEnabled(false);
            downloadb.setEnabled(false);
        }

        JLabel noteSaveLabel = new JLabel("Hold ctrl and click to choose more");
        noteSaveLabel.setBounds(250, 540, 230, 30);

        this.add(saveAllrb);
        this.add(saveSomerb);
        this.add(downloadb);
        this.add(noteSaveLabel);
    }
}
