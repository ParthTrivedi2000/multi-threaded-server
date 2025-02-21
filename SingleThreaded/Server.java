import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// So we will create a server.

public class Server {
    // So as we know this application behave as webserver. so as we know it needs to open 2 kind of sockets ryt.
    // 1) 1 socket which listens all the request thrown to this server
    // 2) and next socket is to data transefer for each request accepted (i.e. once server list any request on above
    // 1st socket, now once request is accepted ie. TCP handshake of SSL etc etc jo bhi things required to establish
    // connection has been perfromed, then client and server are ready to transfer data. but these data transfer won't
    // done on the above same socket at which server is listning all the requests. once connection established for any 
    // request then server always opens up another socket with that client to transfer data).

    public void run() throws IOException{
        int listeningPort = 8010;
        // opens a 1st kind of socket at which server always listens up any request thrown to it when server is up
        // try{
            ServerSocket listeningSocket = new ServerSocket(listeningPort);
            // JFYI:- remember currently this server running on your local laptop, so this websocket will be actually
            // opens on your laptop at the mentioned port number (i.e. from somewhere else you can send request to
            // ipAddress of this laptop:port ==> 127.23.34.56:8010)

            listeningSocket.setSoTimeout(10000); // socket will be closed if it stays ideal i.e. without request
            // for 10 secs. In actual production servers this won't happens, they always listens 24*7, but since we are
            // using this local laptop, so I want my ports to be freed up for other usage so setting timeout.
            while(true){
                try {
                System.out.print("Server is Listening on port "+ listeningPort + "...");
                Socket acceptedConnection = listeningSocket.accept(); // so server will stay here for 10 secs to accepts the
                // client requests. Once any request is accepted, this will return socket and we represented that as
                // acceptedSocket. so since server has accepted client request, letting client know via logging.

                // And yes this acceptedSocket is the one about which we talked above, means to data transfer to client.

                // Note:- socket.getRemoteSocketAddress() method will return the client's ip address/socket address:port.
                System.out.println("Request is accepted for client " + acceptedConnection.getRemoteSocketAddress()); 

                // PrintWriter  --> converts code --> bytes and write to outStream and then send in the socket to client.
                // BufferRead --> receive something from socket pipeline as bytes and convert into code.
                BufferedReader getFromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));
                PrintWriter sendToClient = new PrintWriter(acceptedConnection.getOutputStream(),true);

                // sending something from server to the client
                sendToClient.println("Hello from Server");

                // // Closing connections
                // getFromClient.close();
                // sendToClient.close();
                // listeningSocket.close();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            }
        // }catch(SecurityException se){
        //     throw new SecurityException("Security Manager not allows this operation", se);
        // }catch(IllegalArgumentException iae){
        //     throw new IllegalArgumentException("Invalid port", iae);
        // }
    }


    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
