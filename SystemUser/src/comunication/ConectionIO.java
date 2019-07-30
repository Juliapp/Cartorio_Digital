package comunication;
   
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;



public class ConectionIO {
    private final InputStream input; 
    
    public ConectionIO(Socket socket) throws IOException{
        input = socket.getInputStream();       
    }
    

    public void tratar() throws IOException, InterruptedException {
        tratarInput(input);
    }
    
    private void tratarInput(InputStream input) throws IOException{
        byte[] bytes = toByteArray(input);
        if(bytes.length > 0){
            //tratamento.tratarMensagem(bytes);
        }
    }
    
    private byte[] toByteArray(InputStream input) throws IOException{
        DataInputStream dataInputStream = new DataInputStream(input);
        byte buffer[] = new byte[dataInputStream.available()]; 
        dataInputStream.readFully(buffer);
        return buffer;
    }
    
}
