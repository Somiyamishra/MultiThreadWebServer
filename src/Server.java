import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    public Consumer<Socket> getConsumer(){
        return (clientSocket)->{
            try {
                PrintWriter toClient=new PrintWriter(clientSocket.getOutputStream());
                toClient.println("hello from the Server");
                toClient.close();
                clientSocket.close();
            }catch (IOException ex){//if upper koi bhi erroe ata h to wo yaha print kardenge
ex.printStackTrace();

            }
        };
    }
    public  static void main(String[] args) {
        int port = 8010;
        Server server= new Server();
        try {
            ServerSocket serverSocket=new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("Server is lsitneing on port"+port);
            while (true){
                Socket acceptedSocket=serverSocket.accept();//makes new socket
                Thread thread=new Thread(()->server.getConsumer().accept(acceptedSocket));
                //runs function in which the socket will communicate
thread.start();
        }

    }
        catch (IOException ex){
            ex.printStackTrace();
        }

    }
}