package comunication;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Peer {
    private Socket socket;
    private final String ip;
    private final int port;
    private OutputStream output;    

    public Peer(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
 
    public void conect(){
        try {
            socket = createSocket(ip, port);
            output = socket.getOutputStream();
        } catch (IOException ex) {
            System.err.println(ex);
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

    void send(byte[] bytes) {
        try {
            output.write(bytes, 0, bytes.length);
            output.flush(); 
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
}
