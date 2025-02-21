package MultiThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    public Consumer<Socket> getConsumer(){
        return new Consumer<Socket>() {
            @Override
            public void accept(Socket socket){
                try {
                    PrintWriter toClient = new PrintWriter(socket.getOutputStream());
                    BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    toClient.println("Hey from Server");
                    toClient.close();
                    fromClient.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        
    }
    public static void main(String[] args) throws IOException{
        int port = 8010;
        Server server = new Server();
        try {
            ServerSocket listeningSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port + "...");
            Socket acceptedConnection = listeningSocket.accept();
            // So here server has accepted  conenction request. but we don't want continue this communication via this 
            // thread. Bec for each accepted request we want new thread to be opened up and that needs to send and 
            // receive something from the newly created socket.
            Thread socketThread = new Thread(()->server.getConsumer().accept(acceptedConnection));
            socketThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
