package comunication;
   
import controladores.ControllerDeTratamento;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;



public class ConectionIO {
    //tratamento de mensagem e checagem de mensagem
    private final ControllerDeTratamento tratamento;
    private final InputStream input; 
    
    public ConectionIO(Socket socket, ControllerDeTratamento tratamento) throws IOException{
        this.tratamento = tratamento;
        input = socket.getInputStream();       
    }
    

    public void tratar() throws IOException, InterruptedException {
        tratarInput(input);
    }
    
    private void tratarInput(InputStream input) throws IOException{
        byte[] bytes = toByteArray(input);
        if(bytes.length > 0){
            tratamento.tratarMensagem(bytes);
        }
    }
    
    private byte[] toByteArray(InputStream input) throws IOException{
        DataInputStream dataInputStream = new DataInputStream(input);
        byte buffer[] = new byte[dataInputStream.available()]; 
        dataInputStream.readFully(buffer);
        return buffer;
    }
    
    public void fecharSocket(Socket socket) throws IOException{
        socket.close();
    }
    
    
    
}
