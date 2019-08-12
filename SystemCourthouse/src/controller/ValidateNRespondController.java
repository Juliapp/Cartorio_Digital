package controller;

import JPAPersistence.DAO;
import facade.FacadeBack;
import facade.FacadeComunication;
import java.io.IOException;
import java.util.List;
import model.Realty;
import model.UserData;
import org.json.JSONArray;
import org.json.JSONObject;

public class ValidateNRespondController {

    private final DAO dao;
    private final FacadeComunication facadec;
    private final FacadeBack facadeb;
    private UserData userAux;
    
    
    public ValidateNRespondController() throws IOException, ClassNotFoundException{
        dao = new DAO();
        facadec = FacadeComunication.getInstance();
        facadeb = FacadeBack.getInstance();
        userAux = new UserData();
    }

    public void validateUser(JSONObject message) {
        userAux.setAllNull();
        userAux = dao.getUserByEmail(message.getString("email"));
        JSONObject reply = new JSONObject();
        
        if(userAux != null){
            if(userAux.getPassword().equals(message.getString("password"))){
                
                reply.accumulate("reply", "sucessful login");
                reply.accumulate("cpf", userAux.getCpf());
                reply.accumulate("email", userAux.getEmail());
                reply.accumulate("name", userAux.getName());
                reply.accumulate("passsword", userAux.getPassword());
                
                List<Realty> list = dao.getUserRealties(userAux.getCpf());
                JSONArray realties = new JSONArray(list);
                
                reply.accumulate("arrayRealties", realties);
                facadec.sendMessage(reply.toString(), message.getString("host") , message.getInt("port"));
            }
            else{
                reply.accumulate("reply", "Erro");
                reply.accumulate("message", "password incorrect");
            }
        }else{
            reply.accumulate("reply", "Erro");
            reply.accumulate("message", "email is not registrated");
            System.out.println(message.toString());
            System.out.println(reply.toString());
        }
        
        facadec.sendMessage(reply.toString(), message.getString("host") , message.getInt("port"));
    }
    
    public void repassRealty(JSONObject message){
        JSONObject reply = new JSONObject();
        reply.accumulate("reply", "confirmFirstSignarute");
        reply.accumulate("publicKey", facadeb.encodePublicKey());
        facadec.sendMessage(reply.toString(), message.getString("host") , message.getInt("port"));
    }
    
}
