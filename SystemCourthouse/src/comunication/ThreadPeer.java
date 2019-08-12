package comunication;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class ThreadPeer extends Thread{
    private PeersMap peers;
    
    private boolean hasMessageToSend;
    private boolean isMessageToAll;
    private Peer aux;
    private String bufferedMessage;
    
    public ThreadPeer(){
        hasMessageToSend = false;
    }
    
    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            if(hasMessageToSend){
                if(isMessageToAll){
                    Iterator<Peer> ip = peers.getIterPeers();
                    while(ip.hasNext()){
                        ip.next().send(convertToByte(bufferedMessage));
                    }    
                    hasMessageToSend = false;
                }
                else{
                    aux.send(convertToByte(bufferedMessage));
                    hasMessageToSend = false;
                }
                
//                try {
//                    //faz a thread esperar ter uma nova mensagem pra mandar 
//                    Thread.currentThread().wait();
//                } catch (InterruptedException ex) {
//                    System.err.println(ex);
//                }
            }
            
        }
    }

    void sendMessageToAll(String message) {
        hasMessageToSend = true;
        isMessageToAll = true;
    }
    
    public void UpdatePeers(PeersMap peers) {
        this.peers = peers;
    }
    
    public void sendMessage(String message, Peer peer) {
        aux = peer;
        bufferedMessage = message;
        hasMessageToSend = true;
        isMessageToAll = false;
//        Thread.currentThread().notify();
    }
    
    public byte[] convertToByte(String string) {
        return string.getBytes(StandardCharsets.UTF_8);
    }    
    
}
