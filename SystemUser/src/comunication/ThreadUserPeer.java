package comunication;

public class ThreadUserPeer extends Thread{
    private final UserPeer userPeer;
    //pega o input com o singleton
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
