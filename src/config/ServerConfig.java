package src.config;

public class ServerConfig {
    private String smtpHost, pop3Host;
    private int smtpPort, pop3Port;

    public String getSmtpHost() {
        return smtpHost;
    }

    public String getPop3Host() {
        return pop3Host;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public int getPop3Port() {
        return pop3Port;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public void setPop3Host(String pop3Host) {
        this.pop3Host = pop3Host;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public void setPop3Port(int pop3Port) {
        this.pop3Port = pop3Port;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "smtpHost='" + smtpHost + '\'' +
                ", smtpPort='" + smtpPort + '\''+
                ", pop3Host='" + pop3Host + '\''+
                ", pop3Port='" + pop3Port + '\''+
                '}';
    }
}
