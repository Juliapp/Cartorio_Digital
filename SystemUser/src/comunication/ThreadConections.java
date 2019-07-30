package comunication;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadConections extends Thread{
    ConectionIO servidor;

    public ThreadConections(ConectionIO servidor) {
        this.servidor = servidor;
    }
    
    
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {
                servidor.tratar();
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(ThreadConections.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
