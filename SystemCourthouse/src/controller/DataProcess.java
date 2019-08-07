package controller;

import facade.FacadeBack;
import facade.FacadeComunication;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Realty;
import model.UserData;
import org.json.JSONObject;


public class DataProcess {
    private FacadeBack facadeb;
    private FacadeComunication facadec;
    private UserData userAux;
    private Realty realtyAux;
    
    public DataProcess(){
        try {
            facadeb = FacadeBack.getInstance();
            facadec = FacadeComunication.getInstance();
            userAux = new UserData();
            realtyAux = new Realty();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void pullMessage(byte[] inputedBytes){
        JSONObject message = new JSONObject(convertToString(inputedBytes));
        switch(message.getString("request")){
            case "conect":
                facadec.createNewPeerConection(message.getString("host"), message.getInt("port"));
                break;
            case "saveUser":
                userAux.setCpf(message.getString("cpf"));
                userAux.setEmail(message.getString("email"));
                userAux.setName(message.getString("name"));
                userAux.setPassword(message.getString("password"));
                facadeb.saveUser(userAux);
                userAux.setAllNull();
                break;
            case "validate":
                facadeb.validate(message);
                userAux.setAllNull();
                break;
            case "saveRealty":
                
            default:   
                realtyAux.setAddress(message.getString("address"));
                break;
        }
    }
    
    public String convertToString(byte[] dados) {
        return new String(dados, StandardCharsets.UTF_8);
    }    
}
