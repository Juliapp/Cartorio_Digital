package comunication;

import java.util.Iterator;
import model.DataProcess;

public class ThreadConnections{
    private Iterator<ConnectionIO> peerStream;
    //private DataProcess dataProcess;
//    private ConnectionIO peerStream;

    public ThreadConnections() {
        //peerStream = new Iterator<ConnectionIO>();
    }
    
    public void setPeersStream(Iterator<ConnectionIO> newPeerStream){
        this.peerStream = newPeerStream;
    }
    
//    @Override
//    public void run() {
//        while(!Thread.currentThread().isInterrupted()){
//            try {
//                peerStream.processStream();
//            } catch (IOException | InterruptedException ex) {
//                Logger.getLogger(ThreadConnections.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }    
    
    
//    @Override
//    public void run() {
//        while(!Thread.currentThread().isInterrupted()){
//            try {
//                peerStream.processStream();
//            } catch (IOException | InterruptedException ex) {
//                Logger.getLogger(ThreadConnections.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
    
}
