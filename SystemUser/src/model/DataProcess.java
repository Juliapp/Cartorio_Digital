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
    

    public String convertToString(byte[] dados) {
        return new String(dados, StandardCharsets.UTF_8);
    }    
}
