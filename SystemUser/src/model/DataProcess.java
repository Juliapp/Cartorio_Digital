package model;

import java.nio.charset.StandardCharsets;

public class DataProcess {
    byte[] buffer;

    public DataProcess() {
        buffer = new byte[]{};
    }
    
    public void pullMessange(byte[] inputedBytes){
        //tratar
    }
    
    public void pushMessange(byte[] newMessage){
        //enviar nova mensagem
    }
    
    public byte[] getMessange(){
        byte[] aux = buffer;
        buffer = new byte[]{};
        return aux;
    }
    
    public byte[] convertToByte(String string) {
        return string.getBytes(StandardCharsets.UTF_8);
    }

    public String convertToString(byte[] dados) {
        return new String(dados, StandardCharsets.UTF_8);
    }    
}
