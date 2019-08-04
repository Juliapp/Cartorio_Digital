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

    public void sendMessage(String message, String host, int port) {
        aux = peers.getPeer(host, port);
        bufferedMessage = message;
        hasMessageToSend = true;
        isMessageToAll = false;
    }
    
    public byte[] convertToByte(String string) {
        return string.getBytes(StandardCharsets.UTF_8);
    }    
    
}
