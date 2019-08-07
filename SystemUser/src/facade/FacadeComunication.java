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
    
    public int getUserPeerPort(){
        return connectionsController.getUserPeerPort();
    }    
    
    public String getUserHost(){
        return connectionsController.getUserHost();
    }
    
    public void createNewPeerConection(String ip, int host) {
       connectionsController.addPeer(ip, host);
    }
    
    public void sendMessage(String message, String host, int port){
        connectionsController.sendMessage(message, host, port);
    }
    
    public void sendMessageToCourthouse(String message){
        connectionsController.sendMessageToCourthouse(message);
    }

    public void conectServer(String ip, int port, String askConectionToServer) {
        connectionsController.conectCourtHouse(ip, port, askConectionToServer); 
    }
}
