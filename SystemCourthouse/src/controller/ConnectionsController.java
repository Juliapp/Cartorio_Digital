package controller;

import comunication.Peer;
import comunication.PeersMap;
import comunication.ThreadPeer;
import comunication.ThreadUserPeer;
import comunication.UserPeer;
import java.net.UnknownHostException;

public class ConnectionsController{
    private UserPeer userPeer;
    private final PeersMap othersPeers;
    private ThreadUserPeer threadUserPeer;
    private ThreadPeer threadPeer;
    

    public ConnectionsController() {
        othersPeers = new PeersMap();
    }
    
    public void initializeUserPeer(int port) throws UnknownHostException{
        if(userPeer == null){
            userPeer = new UserPeer(port);
        }
    }
    
    public void initializeThreads(){
        threadUserPeer = new ThreadUserPeer(userPeer);
        new Thread(threadUserPeer).start();          
        threadPeer = new ThreadPeer();
        new Thread(threadPeer).start();
    }
    
    
    public Peer addPeer(String host, int port){
        Peer p = new Peer(host, port);
        System.out.println(p == null);
        p.conect();
        othersPeers.addPeer(p);
        return p;
    }
    
    public Peer getPeer(String key){
        return othersPeers.getPeer(key);
    }

    public void sendMessage(String message, String host, int port) {
        threadPeer.sendMessage(message, othersPeers.getPeer(host, port));
    }
    
}