package model;

import facade.FacadeBack;
import facade.FacadeComunication;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import org.json.JSONObject;
import util.Cript;

public class DataProcess {
    private FacadeBack facadeb;
    private FacadeComunication facadec;
    private final Cript cript;
    
    public DataProcess(){
        cript = new Cript();
        try {
            facadeb = FacadeBack.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }
    
    
    public void pullMessage(byte[] inputedBytes){
        JSONObject message = new JSONObject(convertToString(inputedBytes));
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
                    user.setRealties(message.getString("realties"));
                    
                    user.setPrKey(message.getString("privateKey"));
                    user.setPuKey(message.getString("publicKey"));

                    facadeb.initializeUser(user);
                    facadeb.checkTrue();
                    break;
                case "sucessful sign":
            
                try {
                    int i = facadeb.addNewRealty(message.getInt("permitidId"), message.getString("publicKey"));
                    if(i > 0){
                        try {
                            JSONObject reply = new JSONObject();
                            reply.accumulate("request", "addRealty");
                            reply.accumulate("Id", facadeb.getUser().getCpf());
                            reply.accumulate("rId", i);
                            
                            facadec = FacadeComunication.getInstance();
                            reply.accumulate("host", facadec.getUserHost());
                            reply.accumulate("port", facadec.getUserPeerPort());
                            facadec.sendMessageToCourthouse(reply.toString());
                        } catch (ClassNotFoundException ex) {
                            System.err.println(ex);
                        }
                    }
                } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | IOException | InvalidKeySpecException ex) {
                    System.err.println(ex);
                }
            
                    break;
                case "sucessful save":
                    facadeb.getUser().addRealty(message.getInt("rId"));
                default:
                    break;
                        
            }    
        }
    
        //tratar
    }
    

    public String convertToString(byte[] dados) {
        return cript.UTF8encode(dados);
    }    
}
