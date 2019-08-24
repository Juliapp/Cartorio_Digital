package util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Pacode de encoders e decoders
 * @author Juliana
 */
public class Cript {

    /**
     *
     * @param data
     * @return
     */
    public String UTF8encode(byte[] data){
        return new String(data, StandardCharsets.UTF_8);
    }
    
    /**
     *
     * @param data
     * @return
     */
    public byte[] UTF8decode(String data){
        return data.getBytes(StandardCharsets.UTF_8);
    }
    
    /**
     *
     * @param data
     * @return
     */
    public String BASE64encode(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }
    
    /**
     *
     * @param data
     * @return
     */
    public byte[] BASE64decode(String data){
        return Base64.getDecoder().decode(data);
    }
    
    /**
     *
     * @param data
     * @return
     */
    public String UTF8BASE64converter(String data){
        return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }
}
