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
        connectionsController.initializeUserPeer(port);
        connectionsController.initializeThreads();
    }
    
    public void createNewPeerConection(String ip, int host) {
       connectionsController.addPeer(ip, host);
    }
    
    public void conectServer(String ip, int host){
        connectionsController.conectCourtHouse(ip, host); 
    }
    
    public void sendMessage(String message, String host, int port){
        connectionsController.sendMessage(message, host, port);
    }
    
}
