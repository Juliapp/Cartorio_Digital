package comunication;

import java.io.IOException;
import java.net.Socket;

public class Peer {
    private Socket socket;
    private final String ip;
    private final int port;
    //private ConnectionIO io;

    public Peer(String ip, int port){
        this.ip = ip;
        this.port = port;
        conect();
    }
 
    private void conect(){
        try {
            socket = createSocket(ip, port);
            //io = new ConnectionIO(socket);
        } catch (IOException ex) {
            //tratar isso aqui
        }
        
    }
        
    public boolean isClosed(){
        return socket.isClosed();
    }

    public Socket getSocket(){
        return socket;
    }
    
    public String getIp(){
        return ip;
    }
    
    public int getPort(){
        return port;
    }
    
    private Socket createSocket(String host, int porta) throws IOException {
        return new Socket(host, porta);
    }   
    
    public void closeSocket(Socket socket) throws IOException{
        socket.close();
    }         
}
