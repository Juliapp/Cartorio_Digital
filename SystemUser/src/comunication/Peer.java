package comunication;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Peer {
    private Socket socket;
    private final String ip;
    private final int port;
    private OutputStream output;    

    public Peer(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
 
    public void conect() throws IOException{
        socket = new Socket(ip, port);
        output = socket.getOutputStream();        
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
    
    public void closeSocket(Socket socket) throws IOException{
        socket.close();
    }         

    void send(byte[] bytes) {
        try {
            output.write(bytes, 0, bytes.length);
            output.flush(); 
        } catch (IOException ex) {
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
