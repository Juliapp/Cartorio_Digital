package comunication;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class UserPeer {
    private Socket socket;
    private final int port;
    private InputStream input;

    public UserPeer(int port){
        this.port = port;
    }
 
    public void conect(){
        try {
            socket = createSocket(port);
            System.out.println("peer conectado");
            input = socket.getInputStream();
        } catch (IOException ex) {
            //tratar isso aqui
        }
    }
    
    public InputStream getInputStream(){
        return input;
    }
    
    public Socket getSocket(){
        return socket;
    }
    
    public int getPort(){
        return port;
    }
    
    private Socket createSocket(int porta) throws IOException {
        System.out.println("Esperando algum peer se conectar");
        return new ServerSocket(porta).accept();
    }   
    
    public void closeSocket(Socket socket) throws IOException{
        socket.close();
    }
    
}
