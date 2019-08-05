package comunication;

public class ThreadUserPeer extends Thread{
    private final UserPeer userPeer;
    
    public ThreadUserPeer(UserPeer userPeer) {
        this.userPeer = userPeer;
    }
    
    @Override
    public void run(){
        userPeer.conect();
        while(!Thread.currentThread().isInterrupted()){

            
        }        
    }
    
    
    
}
