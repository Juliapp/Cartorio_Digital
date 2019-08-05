package model;

import facade.FacadeBack;
import facade.FacadeComunication;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;


public class DataProcess {
    private FacadeBack facadeb;
    private FacadeComunication facadec;
    
    public DataProcess(){
        try {
            facadeb = FacadeBack.getInstance();
            facadec = FacadeComunication.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void pullMessage(byte[] inputedBytes){
        String info = convertToString(inputedBytes);
        System.out.println(info);
        JSONObject message = new JSONObject(convertToString(inputedBytes));
        switch(message.getString("request")){
            case "conect":
                facadec.createNewPeerConection(message.getString("host"), message.getInt("port"));
            default:   
        }
    }
    

    public String convertToString(byte[] dados) {
        return new String(dados, StandardCharsets.UTF_8);
    }    
}
