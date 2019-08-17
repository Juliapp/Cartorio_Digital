package controller;

import comunication.Peer;
import comunication.PeersMap;
import comunication.ThreadPeer;
import comunication.ThreadUserPeer;
import comunication.UserPeer;
import java.io.IOException;
import java.net.UnknownHostException;

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
        try {
            userPeer = new UserPeer(port);
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        }
    }
    
    public int getUserPeerPort(){
        return userPeer.getPort();
    }
    
    public String getUserHost(){
        return userPeer.getHost();
    }
    
    public void initializeThreads(){
        threadUserPeer = new ThreadUserPeer(userPeer);
        new Thread(threadUserPeer).start();          
        threadPeer = new ThreadPeer();
        new Thread(threadPeer).start();
    }
    
    public void conectCourtHouse(String host, int port, String askConectionToServer) {
        try {
            Peer p = new Peer(host, port);
            p.conect();
            othersPeers.ConectServer(p);
            threadPeer.UpdatePeers(othersPeers);
            threadPeer.sendMessageToCourthouse(askConectionToServer);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    public Peer addPeer(String host, int port, String askConection){
        try {
            Peer p = new Peer(host, port);
            p.conect();
            othersPeers.addPeer(p);
            threadPeer.sendMessage(askConection, host, port);
            return p;
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return null;
    }
        
    public Peer getPeer(String key){
        return othersPeers.getPeer(key);
    }

    public void sendMessage(String message, String host, int port) {
        threadPeer.sendMessage(message, host, port);
    }
    
    public void sendMessageToCourthouse(String message) {
        threadPeer.sendMessageToCourthouse(message);
    }  

    public void addPeer(String host, int port) {
        try {
            Peer p = new Peer(host, port);
            p.conect();
            othersPeers.addPeer(p);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}