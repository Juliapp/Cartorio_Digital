package facade;

import java.io.FileNotFoundException;
import java.io.IOException;
import model.DataProcess;

public class FacadeBack {
    
    private static FacadeBack facade;
    
    public static synchronized FacadeBack getInstance() throws IOException, FileNotFoundException, ClassNotFoundException {
        return (facade == null) ? facade = new FacadeBack(): facade;
    }        

    public FacadeBack() {
    }
    
    
}