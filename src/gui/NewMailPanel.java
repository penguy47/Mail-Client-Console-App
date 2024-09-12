package src.gui;

import javax.swing.*;

import src.EmailController;

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
    JButton submitButton;
    JButton resetButton;

    public NewMailPanel(EmailController emailController, Font texFont){
        this.emailController = emailController;
        this.texFont = texFont;
        this.setBounds(0,0,100,200);
        this.setFocusable(false);
        this.setLayout(null);

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
        attachmentTextField.setBounds(110, 400, 500, 28);

        attachmentTextField.setEditable(false);
        

        this.add(toTextField);
        this.add(ccTextField);
        this.add(bccTextField);
        this.add(subjectTextField);
        this.add(attachmentTextField);
    }

    private void createTextArea(){
        bodyTextArea = new JTextArea();
        bodyTextArea.setFont(texFont);
        bodyTextArea.setBounds(110, 140, 600, 250);
        bodyTextArea.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        bodyTextArea.setLineWrap(true);
        bodyTextArea.setWrapStyleWord(true);
        this.add(bodyTextArea);
    }

    private void createButton(){
        submitButton = new JButton("Submit");
        fileChooseButton = new JButton("Add file");
        resetButton = new JButton("Reset");

        submitButton.setFocusable(false);
        fileChooseButton.setFocusable(false);
        resetButton.setFocusable(false);

        submitButton.setBounds(300, 440, 88,30);
        resetButton.setBounds(400, 440, 88,30);
        fileChooseButton.setBounds(620, 400, 88,30);

        this.add(submitButton);
        this.add(resetButton);
        this.add(fileChooseButton);
    }
}
