package comunication;

import java.io.IOException;
import java.net.Socket;

public class Conection{
    private Socket socket;
    private String ip;
    private int port;
    private ConectionIO io;

    public Conection(String ip, int port){
        this.ip = ip;
        this.port = port;
        conect();
    }
 
    private void conect(){
        try {
            socket = createSocket(ip, port);
            io = new ConectionIO(socket);
        } catch (IOException ex) {
        }
        
    }
        
    public ConectionIO getConectionIO(){
        return io;
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
        return new Socket(host,porta);
    }   
    
    public void closeSocket(Socket socket) throws IOException{
        socket.close();
    }     
}


