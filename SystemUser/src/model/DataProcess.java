package model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DataProcess {
//    private final BufferMessage message;
    
    public DataProcess() throws IOException, ClassNotFoundException{
        //message = BufferMessage.getInstance();
    }
    
    
    public void pullMessage(byte[] inputedBytes){
        
        //tratar
    }
    
    public void pushMessage(byte[] newMessage){
        //enviar nova mensagem
    }
    
    public byte[] getMessage(){
        return null;
//        byte[] aux = buffer;
//        buffer = new byte[]{};
//        return aux;
    }
    
    public byte[] convertToByte(String string) {
        return string.getBytes(StandardCharsets.UTF_8);
    }

    public String convertToString(byte[] dados) {
        return new String(dados, StandardCharsets.UTF_8);
    }    
}
