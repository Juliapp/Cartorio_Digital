package facade;

import controller.ConnectionsController;
import java.io.IOException;

public class FacadeComunication {
    private final ConnectionsController connectionsController;
    private static FacadeComunication facade;
    
    private FacadeComunication(){
        connectionsController = new ConnectionsController();
    }
    
    public static synchronized FacadeComunication getInstance() throws IOException, ClassNotFoundException {
        return (facade == null) ? facade = new FacadeComunication(): facade;
    }    
    
    public void initializeUserPeer(int port){
        connectionsController.initializeUserPeer(5555);
        connectionsController.initializeThreads();
        createNewPeerConection("localhost", 1234);
    }
    
    public void createNewPeerConection(String ip, int host) {
       connectionsController.addPeer(ip, host);
    }
    
    public void sendMessage(){
        
    }
  
}
