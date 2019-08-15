package comunication;

import java.util.Iterator;
import util.Cript;

public class ThreadPeer extends Thread {

    private PeersMap peers;

    private boolean hasMessageToSend;
    private boolean isMessageToAll;
    private Peer aux;
    private String bufferedMessage;

    private final PC pc;
    private final Cript cript;
    
    public ThreadPeer() {
        hasMessageToSend = false;
        pc = new PC();
        cript = new Cript();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                pc.makeThreadWait();
                if (hasMessageToSend) {
                    if (isMessageToAll) {
                        Iterator<Peer> ip = peers.getIterPeers();
                        while (ip.hasNext()) {
                            ip.next().send(convertToByte(bufferedMessage));
                        }
                        hasMessageToSend = false;
                    } else {
                        aux.send(convertToByte(bufferedMessage));
                        hasMessageToSend = false;
                    }
                }
                
            } catch (InterruptedException ex) {
                System.err.println(ex);
                Thread.currentThread().interrupt();
            }

        }
    }

    void sendMessageToAll(String message) {
        hasMessageToSend = true;
        isMessageToAll = true;
        try {
            pc.makeThreadWakeUp();
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }

    public void UpdatePeers(PeersMap peers) {
        this.peers = peers;
    }

    public void sendMessage(String message, Peer peer) {
        aux = peer;
        bufferedMessage = message;
        hasMessageToSend = true;
        isMessageToAll = false;
        try {
            pc.makeThreadWakeUp();
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }

    public byte[] convertToByte(String string) {
        return cript.UTF8decode(string);
    }
    
    private static class PC{
        public void makeThreadWait() throws InterruptedException {
            synchronized (this) {
                wait();
            }
        }

        public void makeThreadWakeUp() throws InterruptedException {
            synchronized (this) {
                notify();
            }
        }        
    }    

}
