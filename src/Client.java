import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public Runnable getRunnable() { // Runnable ek aisa function hai jo kuch accept nahi karta, bas ek hi method hai 'run'
        return new Runnable() {
            @Override
            public void run() {
                int port = 8010;
                try {
                    InetAddress address = InetAddress.getByName("localhost");
                    Socket socket = new Socket(address, port);
                    try {
                        PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        // Send a message to the server
                        toSocket.println("Hello from the Client!");

                        // Receive and print the response from the server
                        String response = fromSocket.readLine();
                        System.out.println("Server says: " + response);

                    } finally {
                        socket.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args) {
        Client client = new Client();
        for (int i = 0; i < 100; i++) { // It will spawn 100 clients
            try {
                Thread thread = new Thread(client.getRunnable());
                thread.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
