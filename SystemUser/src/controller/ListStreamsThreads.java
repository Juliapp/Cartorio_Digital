package controller;

import comunication.ConnectionIO;
import comunication.ThreadPeerStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListStreamsThreads extends Thread{
    private final List<ThreadPeerStream> threadblePeers;
    
    public ListStreamsThreads(){
        threadblePeers = new ArrayList();
    }
    
    @Override
    public void run(){
        if(threadblePeers.isEmpty())
        while(!Thread.currentThread().isInterrupted()){
            if(!threadblePeers.isEmpty()){
                threadblePeers.forEach((threadblePeer) -> {
    //                threadblePeer.waitFlag();
                    threadblePeer.givePermission();
                    threadblePeer.waitFlag();
                });
            }
        }
    }    
    public void addNewPeearIO(ConnectionIO cIO){
        try {
            ThreadPeerStream tps = new ThreadPeerStream(cIO);
            threadblePeers.add(tps);
            new Thread(tps).start();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("não foi dessa vez");
            //Tratar isso aqui
        }
    }
    
}
