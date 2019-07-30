package comunication;

import controladores.ControllerDeTratamento;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexao{
    private Socket socket;
    private int porta;
    private ConectionIO io;

    private final ControllerDeTratamento tratamento;
    
    public Conexao(ControllerDeTratamento tratamento){
        this.tratamento = tratamento;
    }
 
    public Conexao(ControllerDeTratamento tratamento, String ip, int porta){
        this.tratamento = tratamento;
        try {
            socket = criarSocket(ip, porta);
            io = new ConectionIO(socket, tratamento);
        } catch (IOException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        
    public ConectionIO getConectionIO(){
        return io;
    }

    public Socket getSocket(){
        return socket;

    }
    
     private Socket criarSocket(String host, int porta) throws IOException {
        Socket sock = new Socket();
        return new Socket(host,porta);
    }   
    
}


