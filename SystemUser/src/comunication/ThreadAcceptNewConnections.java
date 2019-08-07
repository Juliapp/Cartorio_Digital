package comunication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadAcceptNewConnections extends Thread{
   private final int port;
   
   public ThreadAcceptNewConnections(int port){
       this.port = port;
   }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {
                Socket newAccept = new ServerSocket(port).accept();
            } catch (IOException ex) {
                System.err.println(ex);
            }
            
        }
    }
    
   
    
}
