package src.entity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import src.utils.EmailFormatter;

public class Mail {
    private String from;
    private List<String> to;
    private List<String> cc;
    private List<String> bcc;
    private String subject;
    private String body;
    private List<Attachment> attachments;

    public Mail() {
        this.to = new ArrayList<>();
        this.cc = new ArrayList<>();
        this.bcc = new ArrayList<>();
        this.attachments = new ArrayList<>();
    }

    // Getters and Setters
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getTo() {
        return to;
    }

    public void addTo(String recipient) {
        this.to.add(recipient);
    }

    public List<String> getCc() {
        return cc;
    }

    public void addCc(String recipient) {
        this.cc.add(recipient);
    }

    public List<String> getBcc() {
        return bcc;
    }

    public void addBcc(String recipient) {
        this.bcc.add(recipient);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
    }

    public String getHeaderString() {
        StringBuilder builder = new StringBuilder();
        builder.append("From: ").append(from + "\r\n")
                    .append("To: " + EmailFormatter.formatToHeader(to).toString()+"\r\n")
                    .append("Cc: "+ EmailFormatter.formatToHeader(cc).toString()+"\r\n")
                    .append("Bcc: "+ EmailFormatter.formatToHeader(bcc).toString()+"\r\n")
                    .append("Subject: " + subject +".\r\n");
        return builder.toString();
    }

    public List<String> getBodyLines() throws IOException {
        List<String> result = new ArrayList<>(); 
        result.add("Content-Type: multipart/mixed; boundary=\"boundary\"\r\n");
        result.add("--boundary\r\n");
        result.add(body + "\r\n\r\n");

        for(Attachment attachment : attachments){
            result.add("--boundary_string\r\n");
            result.add("Content-Type: application/octet-stream\r\n");
            result.add("Content-Transfer-Encoding: base64\r\n");
            result.add("Content-Disposition: attachment; filename=\"" + attachment.getName()+"\"\r\n");
            result.add("\r\n");


            result.addAll(attachment.getBase64Encoded());

            result.add("\r\n");
        }

        result.add("--boundary_string--\r\n");

        return result;

    }

    @Override
    public String toString() {
        return "Mail{" +
                "from='" + from + '\'' +
                ", to=" + to +
                ", cc=" + cc +
                ", bcc=" + bcc +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", attachments=" + attachments +
                '}';
    }
}
