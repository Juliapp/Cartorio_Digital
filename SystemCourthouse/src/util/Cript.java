package util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class Cript {

    public String UTF8encode(byte[] data){
        return new String(data, StandardCharsets.UTF_8);
    }
    
    public byte[] UTF8decode(String data){
        return data.getBytes(StandardCharsets.UTF_8);
    }
    
    public String BASE64encode(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }
    
    public byte[] BASE64decode(String data){
        return Base64.getDecoder().decode(data);
    }
    
    public String UTF8BASE64converter(String data){
        return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }
}
