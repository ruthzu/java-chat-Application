import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 8000;
    private static List<PrintWriter> clientWriters = new ArrayList<>(); // To broadcast messages to all clients

    public static void main(String[] args) {
        System.out.println("Chat Server is running on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Wait for client connection
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                
                // Create a new thread for each client
                ClientHandler handler = new ClientHandler(clientSocket);
                handler.start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                
                synchronized (clientWriters) {
                    clientWriters.add(out); // Add this client's writer for broadcasting
                }
                
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("exit")) {
                        break;
                    }
                    System.out.println("Received: " + message);
                    broadcast(message); // Send to all clients
                }
            } catch (IOException e) {
                System.err.println("Client error: " + e.getMessage());
            } finally {
                try {
                    if (out != null) {
                        synchronized (clientWriters) {
                            clientWriters.remove(out);
                        }
                    }
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error closing socket: " + e.getMessage());
                }
            }
        }

        private void broadcast(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    writer.println(message);
                }
            }
        }
    }
}