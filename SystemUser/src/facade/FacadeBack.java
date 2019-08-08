package facade;


import model.DataCheck;
import java.io.IOException;
import java.util.List;
import model.ObserverData;
import model.Realty;
import model.UserData;

public class FacadeBack {
    
    private UserData user;
    private static FacadeBack facade;
    private final DataCheck datacheck;
    
    public static synchronized FacadeBack getInstance() throws IOException, ClassNotFoundException {
        return (facade == null) ? facade = new FacadeBack(): facade;
    }        

    public FacadeBack() {
        datacheck = new DataCheck();
    }
    
    public void checkTrue(){
        datacheck.setSucessfullLogin(true);
    }
    
    public void checkFalse(){
        datacheck.setSucessfullLogin(false);
    }
    
    public DataCheck getDataCheck(){
        return datacheck;
    }
    
    public void addObservable(ObserverData observer){
        datacheck.addObserver(observer);
    }
    

    public void initializeUser(UserData user){
        this.user = user;
    }
    
    public UserData getUser(){
        return user;
    }

    public List<Realty> getUserRealties() {
        return user.getRealties();
    }

}