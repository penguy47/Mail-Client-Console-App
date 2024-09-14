package src.gui;

import javax.swing.*;

import java.util.List;
import java.util.ArrayList;

import src.EmailController;
import src.entity.Attachment;
import src.entity.Mail;
import src.utils.EmailFormatter;

import java.awt.*;


public class NewMailPanel extends JPanel {
    EmailController emailController;
    Font texFont;

    JLabel toLabel;
    JLabel ccLabel;
    JLabel bccLabel;
    JLabel subjectLabel;
    JLabel bodyLabel;
    JLabel attachmentLabel;

    JTextField toTextField;
    JTextField ccTextField;
    JTextField bccTextField;
    JTextField subjectTextField;
    JTextField attachmentTextField;

    JTextArea bodyTextArea;

    JButton fileChooseButton;
    JButton fileResetButton;
    JButton submitButton;
    JButton resetButton;

    List<Attachment> attachments;

    public NewMailPanel(EmailController emailController, Font texFont){
        this.emailController = emailController;
        this.texFont = texFont;
        this.setBounds(0,0,100,200);
        this.setFocusable(false);
        this.setLayout(null);

        this.attachments = new ArrayList<>();

        createLabels();
        createTextField();
        createTextArea();
        createButton();
    }

    void createLabels(){
        toLabel = new JLabel("To: ");
        ccLabel = new JLabel("Cc: ");
        bccLabel = new JLabel("Bcc: ");
        subjectLabel = new JLabel("Subject: ");
        bodyLabel = new JLabel("Body: ");
        attachmentLabel = new JLabel("Files: ");

        toLabel.setFont(texFont);
        ccLabel.setFont(texFont);
        bccLabel.setFont(texFont);
        subjectLabel.setFont(texFont);
        bodyLabel.setFont(texFont);
        attachmentLabel.setFont(texFont);

        toLabel.setBounds(20,20,80,30);
        ccLabel.setBounds(20,50,80,30);
        bccLabel.setBounds(20,80,80,30);
        subjectLabel.setBounds(20,110,80,30);
        bodyLabel.setBounds(20,140,80,30);
        attachmentLabel.setBounds(20,400,80,30);

        this.add(toLabel);
        this.add(ccLabel);
        this.add(bccLabel);
        this.add(subjectLabel);
        this.add(bodyLabel);
        this.add(attachmentLabel);
    }

    private void createTextField(){
        toTextField = new JTextField();
        ccTextField = new JTextField();
        bccTextField = new JTextField();
        subjectTextField = new JTextField();
        attachmentTextField = new JTextField();

        toTextField.setFont(texFont);
        ccTextField.setFont(texFont);
        bccTextField.setFont(texFont);
        subjectTextField.setFont(texFont);
        attachmentTextField.setFont(texFont);

        toTextField.setBounds(110, 20, 600, 28);
        ccTextField.setBounds(110, 50, 600, 28);
        bccTextField.setBounds(110, 80, 600, 28);
        subjectTextField.setBounds(110, 110, 600, 28);

        attachmentTextField.setEditable(false);

        JScrollPane scrollPaneAttachmentText = new JScrollPane(attachmentTextField);
        scrollPaneAttachmentText.setBounds(110, 400, 400, 45);
        scrollPaneAttachmentText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneAttachmentText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        

        this.add(toTextField);
        this.add(ccTextField);
        this.add(bccTextField);
        this.add(subjectTextField);
        this.add(scrollPaneAttachmentText);
    }

    private void createTextArea(){
        bodyTextArea = new JTextArea();
        bodyTextArea.setFont(texFont);
        
        bodyTextArea.setLineWrap(true);
        bodyTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(bodyTextArea);
        scrollPane.setBounds(110, 140, 600, 250);
        this.add(scrollPane);
    }

    private void createButton(){
        submitButton = new JButton("Submit");
        fileChooseButton = new JButton("Add file");
        fileResetButton = new JButton("Empty");
        resetButton = new JButton("Reset");

        submitButton.setFocusable(false);
        fileChooseButton.setFocusable(false);
        fileResetButton.setFocusable(false);
        resetButton.setFocusable(false);

        submitButton.setBounds(300, 455, 88,30);
        resetButton.setBounds(400, 455, 88,30);
        fileChooseButton.setBounds(520, 400, 88,30);
        fileResetButton.setBounds(620, 400, 88,30);

        createEventListeners();

        this.add(submitButton);
        this.add(resetButton);
        this.add(fileChooseButton);
        this.add(fileResetButton);
    }

    private void createEventListeners(){
        resetButton.addActionListener((e) -> {
            toTextField.setText("");
            ccTextField.setText("");
            bccTextField.setText("");
            subjectTextField.setText("");
            bodyTextArea.setText("");
        });

        fileChooseButton.addActionListener((e) -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if(returnValue == JFileChooser.APPROVE_OPTION){
                attachments.add(new Attachment(fileChooser.getSelectedFile()));
            } 
            // re-render attachment text field
            attachmentTextField.setText("");
            List<String> attachmentNames = attachments.stream().map((ath) -> {
                                            return "[" + ath.getName() + "]";
                                        }).toList();

            attachmentTextField.setText(EmailFormatter.formatToHeader(attachmentNames));
        });

        fileResetButton.addActionListener((e) -> {
            attachments.clear();
            attachmentTextField.setText("");
        });

        submitButton.addActionListener((e) -> {

            Mail mail = new Mail();
            mail.setFrom(emailController.user.email);
            for(String str : toTextField.getText().split(",")){
                mail.addTo(str.trim());
            }
            for(String str : ccTextField.getText().split(",")){
                mail.addCc(str.trim());
            }
            for(String str : bccTextField.getText().split(",")){
                mail.addBcc(str.trim());
            }
            mail.setSubject(subjectTextField.getText());
            mail.setBody(bodyTextArea.getText());
            for( Attachment a : attachments){
                mail.addAttachment(a);
            }

            emailController.sendMail(mail);
        });
    }
}
