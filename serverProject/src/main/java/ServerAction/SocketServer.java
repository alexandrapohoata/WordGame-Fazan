package ServerAction;//package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private static final int PORT = 8100;
    private ServerSocket serverSocket;
    private boolean running = true;

    public static void main(String[] args) throws IOException {
        SocketServer server = new SocketServer();
        server.init();
    }

    private void init() throws IOException {
        try {
            serverSocket = new ServerSocket(PORT);
            //while running is true, create a new socket for every incoming client and start a server.ClientThread to execute its request
            while (running) {
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                // Execute the client's request in a new thread
                new ClientThread(socket, this).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }

        running = true;
    }
}
