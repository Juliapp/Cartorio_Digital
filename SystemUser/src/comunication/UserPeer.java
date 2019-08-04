package comunication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class UserPeer {
    private Socket socket;
    private final int port;
    //private ConnectionIO io;

    public UserPeer(int port){
        this.port = port;
    }
 
    public void conect(){
        try {
            socket = createSocket(port);
            //io = new ConnectionIO(socket);
        } catch (IOException ex) {
            //tratar isso aqui
        }
    }
    
    
        
    public Socket getSocket(){
        return socket;
    }
    
    public int getPort(){
        return port;
    }
    
    private Socket createSocket(int porta) throws IOException {
        return new ServerSocket(porta).accept();
    }   
    
    public void closeSocket(Socket socket) throws IOException{
        socket.close();
    }         
}
