package facade;

import controller.ConnectionsController;
import java.io.IOException;
import java.net.UnknownHostException;

public class FacadeComunication {
    private final ConnectionsController connectionsController;
    private static FacadeComunication facade;
    
    private FacadeComunication(){
        connectionsController = new ConnectionsController();
    }
    
    public static synchronized FacadeComunication getInstance() throws IOException, ClassNotFoundException {
        return (facade == null) ? facade = new FacadeComunication(): facade;
    }    
    
    public void initializeCourtHouse(int port) throws UnknownHostException{
        connectionsController.initializeUserPeer(port);
        connectionsController.initializeThreads();
    }
    
    public void createNewPeerConection(String ip, int host) {
       connectionsController.addPeer(ip, host);
    }
    
    public void sendMessage(String message, String host, int port){
        connectionsController.sendMessage(message, host, port);
    }
    
}
