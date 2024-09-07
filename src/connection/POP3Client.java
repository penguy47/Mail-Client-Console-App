package src.connection;

import java.io.*;
import java.net.Socket;

public class POP3Client {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public POP3Client(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        // First res from server
        System.out.println("[POP3Client]: " + reader.readLine());
    }

    public void sendCommand(String command) throws IOException {
        writer.write(command + "\r\n");
        writer.flush();
        System.out.println("[Server]: " + reader.readLine());
    }

    public String nextLine() throws IOException {
        if(reader != null) return reader.readLine();
        else return "";
    }

    public void disconnect() throws IOException {
        sendCommand("QUIT");
        socket.close();
    }
}
