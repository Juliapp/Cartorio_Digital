package comunication;

import java.util.Iterator;

public class ThreadPeer extends Thread{
    private Iterator<Peer> peers;
    private boolean flag = true;
    
    public ThreadPeer(){
        peers = null;
    }
    
    @Override
    public void run(){
        waitPeer();
        while(!Thread.currentThread().isInterrupted()){
            
        }
    }
    
    private synchronized void waitPeer(){
        while(peers == null){
            try {
                wait();
            } catch (InterruptedException ex) {
                interrupt();
            }
        }
    } 

    void sendMessage(String message) {

    }
    
    public void UpdatePeers(Iterator<Peer> iterPeers) {
        peers = iterPeers;
    }
    
}
