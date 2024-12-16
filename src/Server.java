import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    public Consumer<Socket> getConsumer() {
        return (clientSocket) -> {
            try {
                // Send a message to the client
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
                toClient.println("Hello from the Server!");
                System.out.println("Message sent to client");

                // Close the resources
                toClient.close();
                clientSocket.close();
            } catch (IOException ex) {
                // Print any errors that occur
                ex.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(10000); // Set a timeout of 10 seconds
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket acceptedSocket = serverSocket.accept(); // Accept a new client connection
                Thread thread = new Thread(() -> server.getConsumer().accept(acceptedSocket));
                thread.start(); // Handle each client in a new thread
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
