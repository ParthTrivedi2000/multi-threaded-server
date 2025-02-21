import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public void run() throws UnknownHostException, IOException{
        int port = 8010; // Always remember. this is the port where server must be listning else you get connection refused
        //error.
        InetAddress address = InetAddress.getByName("localhost");
        
        // as server is having socket and from socket it sends something to outside world, similarly client also wants
        // to send something to outside world, so obviously it also needs to have the socket. And sent into that socket
        // and receive from that socket.
        Socket clientSocket = new Socket(address, port);
        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(),true);
        toSocket.println("Hello from client side...");

        // how to get something from socket?
        // buffer reader will get bytes from socket and convert into string and return it.
        String somethingFromSocket = fromSocket.readLine();
        System.out.println("Response from socket is " + somethingFromSocket);

        // Closing connections
        fromSocket.close();
        toSocket.close();
        clientSocket.close();
    }

    public static void main(String[] args) {
        System.out.println("Hey There from client");
        try {
            Client client = new Client();
            client.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
