package ClientComunication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientComunication {
    String serverAddress = "127.0.0.1"; // The server's IP address
    int PORT = 8100; // The server's port
    Socket socket;
    PrintWriter out;
    BufferedReader in;

    public ClientComunication() throws IOException {
        this.socket = new Socket(serverAddress, PORT);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
    }

    public void sendRequest(String request) throws IOException//sent to server my request
    {
        this.out.println(request);

    }

    public String readRequest() throws IOException //read response
    {
        return this.in.readLine();
    }

}
