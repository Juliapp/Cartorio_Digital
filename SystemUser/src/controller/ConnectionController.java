package controller;

import comunication.Connection;
import comunication.ConnectionIO;
import comunication.PeersMap;
import java.util.Iterator;

public class ConnectionController {
    private final PeersMap peers;
    
    public ConnectionController(){
        peers = new PeersMap();
    }

    public Connection createNewPeerConection(String ip, int host) {
       return peers.createNewPeerConection(ip, host);
    }
    
    public Iterator<String> getAllIps(){
        return peers.getAllIps();
    }
    
    public Iterator<Connection> getAllConnections(){
        return peers.getAllConnections();
    }
    
    public Iterator<ConnectionIO> getAllConnectionsIO(){
        return peers.getAllConnectionsIO();
    }
}
