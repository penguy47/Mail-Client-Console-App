package connection;

import java.io.*;
import java.net.Socket;

public class SMTPClient {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public SMTPClient(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        // First res from server
        System.out.println("[SMTPClient]: " + reader.readLine());
    }

    private void sendCommand(String command) throws IOException {
        writer.write(command + "\r\n");
        writer.flush();
        System.out.println(reader.readLine());
    }

    public void disconnect() throws IOException {
        sendCommand("QUIT");
        socket.close();
    }
}
