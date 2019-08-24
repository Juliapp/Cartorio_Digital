package comunication;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 *Conexões que fazemos com os outros sistemas
 * @author Juliana
 */
public class Peer {
    private Socket socket;
    private final String ip;
    private final int port;
    private OutputStream output;    

    public Peer(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
 
    /**
     *Se conecta com o serverSocket de algum peer dado seu host e sua porta
     */
    public void conect(){
        try {
            socket = createSocket(ip, port);
            output = socket.getOutputStream();
        } catch (IOException ex) {
            System.err.println(ex);
        }
        
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
    
    /**
     *Close this connection 
     * @param socket
     * @throws IOException
     */
    public void closeSocket(Socket socket) throws IOException{
        socket.close();
    }         

    /**
     *Manda uma mensagem para esse peer específico 
     * @param bytes mensagem em bytes 
     */
    public void send(byte[] bytes) {
        try {
            output.write(bytes, 0, bytes.length);
            output.flush(); 
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
}
