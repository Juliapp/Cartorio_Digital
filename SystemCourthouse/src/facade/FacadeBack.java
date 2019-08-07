package facade;

import JPAPersistence.DAO;
import controller.ValidateNRespondController;
import java.io.IOException;
import java.util.List;
import model.Realty;
import model.UserData;
import org.json.JSONObject;

public class FacadeBack {
    
    private static FacadeBack facade;
    
    private final DAO dao;
    private final ValidateNRespondController validate;
    
    private FacadeBack() throws IOException, ClassNotFoundException{
        dao = new DAO();
        validate = new ValidateNRespondController();
    }
    
    public static synchronized FacadeBack getInstance() throws IOException, ClassNotFoundException {
        return (facade == null) ? facade = new FacadeBack(): facade;
    }        

    

    public Realty saveRealty(Realty realty){
        return dao.saveRealty(realty);
    }
    
    public Realty findRealtyById(Integer id){
        return dao.findRealtyById(id);
    }    
   
    public List<Realty> getAllRealties(){
        return dao.getAllRealties();
    }
    
    public Realty removeRealty(Integer id){
        return dao.removeRealty(id);
    }
    
    /*
        PERSISTENCE OF USERS    
    */    
    
    public UserData saveUser(UserData user){
        return dao.saveUser(user);
    }
    
    public UserData getUserById(String id){
        return dao.getUserById(id);
    }    
   
    public List<UserData> getAllUsers(){
        return dao.getAllUsers();
    }
    
    public UserData removeUser(String id){
        return dao.removeUser(id);
    }
    
    public List<Realty> getUserRealties(String id){
        return dao.getUserRealties(id);
    }
    
    public UserData getUserByEmail(String email){
        return dao.getUserByEmail(email);
    }

    public void validate(JSONObject message) {
        validate.validateUser(message);
    }
}