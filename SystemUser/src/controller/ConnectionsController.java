package controller;

import comunication.Peer;
import comunication.PeersMap;
import comunication.ThreadPeer;
import comunication.ThreadUserPeer;
import comunication.UserPeer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionsController{
    private UserPeer userPeer;
    private final PeersMap othersPeers;
    private ThreadUserPeer threadUserPeer;
    private ThreadPeer threadPeer;
    

    public ConnectionsController() {
        othersPeers = new PeersMap();
        userPeer = null;
    }
    
    public void initializeUserPeer(int port){
        userPeer = new UserPeer(port);
    }
    
    public int getUserPeerPort(){
        return userPeer.getPort();
    }
    
    public void initializeThreads(){
        threadUserPeer = new ThreadUserPeer(userPeer);
        new Thread(threadUserPeer).start();          
        threadPeer = new ThreadPeer();
        new Thread(threadPeer).start();
    }
    
    
    public Peer addPeer(String host, int port){
        Peer p = new Peer(host, port);
        try {
            p.conect();
            if(othersPeers.addPeer(p) != null){
                threadPeer.UpdatePeers(othersPeers);
            }            
        } catch (IOException ex) {
            Logger.getLogger(ConnectionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
        
    public Peer getPeer(String key){
        return othersPeers.getPeer(key);
    }

    public void sendMessage(String message, String host, int port) {
        addPeer(host, port);
        threadPeer.sendMessage(message, host, port);
    }
    
    public void sendMessageToCourthouse(String message) {
        threadPeer.sendMessageToCourthouse(message);
    }  

    public void conectCourtHouse(String host, int port, String askConectionToServer) {
        try {
            Peer p = new Peer(host, port);
            p.conect();
            othersPeers.ConectServer(p);
            threadPeer.UpdatePeers(othersPeers);
            threadPeer.sendMessageToCourthouse(askConectionToServer);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}