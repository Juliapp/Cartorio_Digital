package model;

import facade.FacadeBack;
import facade.FacadeComunication;
import facade.FacadeFront;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import javafx.application.Platform;
import org.json.JSONObject;
import util.Cript;

/**
 *Controle de recebimento de mensagens
 * @author Juliana
 */
public class DataProcess {
    private FacadeBack facadeb;
    private FacadeComunication facadec;
    private FacadeFront facadef;
    private final Cript cript;
    
    public DataProcess(){
        cript = new Cript();
        try {
            facadeb = FacadeBack.getInstance();
            facadef = FacadeFront.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }
  
    /**
     *Insere a mensagem, transforma em um JSON e a processa
     * @param inputedBytes
     * @throws ClassNotFoundException
     * @throws SQLException
     */    
    
    public void pullMessage(byte[] inputedBytes) throws ClassNotFoundException, SQLException{
        JSONObject message = new JSONObject(convertToString(inputedBytes));
        System.out.println(message.toString());
        if(message.getString("reply") != null){
            switch(message.getString("reply")){
                case "Erro":
//                    facadef.newAlertError("erro", message.getString("message"));
//                    facadeb.checkFalse();
                    break;
                case "sucessful login": 
                    UserData user = new UserData();
                    user.setCpf(message.getString("cpf"));
                    user.setEmail(message.getString("email"));
                    user.setName(message.getString("cpf"));
                    user.setPassword(message.getString("password"));
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
                    break;
                    
                case "Repassing Realty":
                    facadeb.setPassword(message.getString("password"));
                    facadeb.setRealtyToPass(message.getInt("rId"));
                    facadeb.setSellerhost(message.getString("host"));
                    facadeb.setSellerport(message.getInt("port"));
                    facadeb.setPublicKey(message.getString("publicKey"));
                    Platform.runLater(facadef::reciveScreen);
                    break;
                    
                case "sucessful repass":
                    JSONObject reply = new JSONObject();
                    reply.accumulate("Id", facadeb.getUser().getCpf());
                    reply.accumulate("request", "userRemoveRealty");
                    reply.accumulate("rId", message.getInt("rId"));
                    facadec.sendMessageToCourthouse(reply.toString());
                    facadeb.getUser().removeRealty(message.getInt("rId"));
                    break;
                    
                case "search":
                    facadef.setSearchResult(message.getString("result"));
                    break;
                    
                case "conect":
            
                try {
                    facadec = FacadeComunication.getInstance();
                    facadec.createNewPeerConection(message.getString("host"), message.getInt("port"));
                } catch (IOException | ClassNotFoundException ex) {
                    System.err.println(ex);
                }
                    break;
                
                default:
                    System.out.println("não entrou em nenhum");
                    break;
                        
            }    
        }
    
    }
    

    public String convertToString(byte[] dados) {
        return cript.UTF8encode(dados);
    }    
}
