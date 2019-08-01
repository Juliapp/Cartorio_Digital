package comunication;

import java.io.IOException;

public class ThreadPeerStream extends Thread{
    private final ConnectionIO peerStream;
    private final BufferMessage buffer;
    private boolean flag;
    
    /**
     *
     * @param peerStream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ThreadPeerStream(ConnectionIO peerStream) throws IOException, ClassNotFoundException {
        this.peerStream = peerStream;
        buffer = BufferMessage.getInstance();
        flag = false;
    }
    
    @Override
    public void run() {
        while(!peerStream.isClosed()){
            stream();
            //Tratar pra não mandar e nem receber uma mensagem mais de uma vez
            this.flag = false;
            notify();
            //esperar
        }
    }

    private void stream(){
        byte[] auxToSend = buffer.getToSend();
        byte[] auxRecive = peerStream.processStream(auxToSend);
        buffer.addMessageToRecive(auxRecive);        
    }
    
    public void givePermission(){
        this.flag = true;
    }
    public synchronized void waitFlag(){
        while(!this.flag){
            try {
                wait();
            } catch (InterruptedException ex) {
                //Logger.getLogger(ThreadPeerStream.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
