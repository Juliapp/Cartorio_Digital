package facade;

import java.io.IOException;
import java.util.Iterator;
import model.Realty;
import model.UserData;

public class FacadeBack {
    
    private final UserData user;
    private static FacadeBack facade;
    
    public static synchronized FacadeBack getInstance() throws IOException, ClassNotFoundException {
        return (facade == null) ? facade = new FacadeBack(): facade;
    }        

    public FacadeBack() {
        user = new UserData("João");
    }
    
    public Iterator<Realty> getRealties(){
        
        return user.getIterRelties();
    }

    public void initializeUser(){
        user.addRealty(new Realty(1, "Rua a"));
        user.addRealty(new Realty(2, "Rua b"));
        user.addRealty(new Realty(3, "Rua c"));
        user.addRealty(new Realty(4, "Rua d"));        
    }
    
    public UserData getUser(){
        return user;
    }
    
}