package model;

import facade.FacadeBack;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class DataProcess {
    private FacadeBack facadeb;
    
    public DataProcess(){
        try {
            facadeb = FacadeBack.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }
    
    
    public void pullMessage(byte[] inputedBytes){
        JSONObject message = new JSONObject(convertToString(inputedBytes));
        System.out.println(message);
        if(message.getString("reply") != null){
            switch(message.getString("reply")){
                case "Erro":
                    String erro = message.getString("message");
                    //fazer um pop up
                    facadeb.checkFalse();
                    break;
                case "sucessful login": 
                    UserData user = new UserData();
                    user.setCpf(message.getString("cpf"));
                    user.setEmail(message.getString("email"));
                    user.setName(message.getString("cpf"));
                    user.setPassword(message.getString("cpf"));
                    facadeb.initializeUser(user);
                    facadeb.checkTrue();
                    break;
            }    
        }
    
        //tratar
    }
    

    public String convertToString(byte[] dados) {
        return new String(dados, StandardCharsets.UTF_8);
    }    
}
