package connection;

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

    private void sendCommand(String command) throws IOException {
        writer.write(command + "\r\n");
        writer.flush();
        String response;
        while (!(response = reader.readLine()).equals(".")) {
            System.out.println(response);
        }
    }

    public void disconnect() throws IOException {
        sendCommand("QUIT");
        socket.close();
    }
}
