package facade;

import comunication.Connection;
import comunication.ConnectionIO;
import comunication.ThreadConnections;
import controller.ConnectionController;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class FacadeComunication {
    private final ConnectionController connectionController;
    private final ThreadConnections threadConections;
    private static FacadeComunication facade;
    
    private FacadeComunication(){
        connectionController = new ConnectionController();
        threadConections = new ThreadConnections();
    }
    
    public static synchronized FacadeComunication getInstance() throws IOException, FileNotFoundException, ClassNotFoundException {
        return (facade == null) ? facade = new FacadeComunication(): facade;
    }    
    
    public Connection createNewPeerConection(String ip, int host) {
       return connectionController.createNewPeerConection(ip, host);
    }
    
    public Iterator<String> getAllIps(){
        return connectionController.getAllIps();
    }
    
    public Iterator<Connection> getAllConnections(){
        return connectionController.getAllConnections();
    }
    
    public Iterator<ConnectionIO> getAllConnectionsIO(){
        return connectionController.getAllConnectionsIO();
    }    
}
