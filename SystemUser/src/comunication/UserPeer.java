package comunication;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class UserPeer {
    private Socket socket;
    private final int port;
    private final String host;
    private InputStream input;

    public UserPeer(int port) throws UnknownHostException{
        this.host = InetAddress.getLocalHost().getHostAddress();
        this.port = port;
    }
 
    void conect(){
        try {
            System.out.println("Esperando algum peer se conectar");
            socket = new ServerSocket(port).accept();
            System.out.println("peer conectado");
            input = socket.getInputStream();
        } catch (IOException ex) {
            System.out.println("ex conect");
        }
    }
    
    public Socket getSocket(){
        return socket;
    }
    
    public int getPort(){
        return port;
    }
    
    public String getHost(){
        return host;
    }
    
    public InputStream getInputStream(){
        return input;
    }
    
    public void closeSocket(Socket socket) throws IOException{
        socket.close();
    }         
      
}
