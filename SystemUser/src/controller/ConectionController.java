package controller;
import comunication.Conection;
import comunication.ThreadConections;
import java.util.ArrayList;

public class ConectionController {
    private final ArrayList<Conection> peers;
   

    public ConectionController() {
        peers = new ArrayList<>();
    }
    
    public void createNewPeerConection(String ip, int host){
        peers.add(new Conection(ip, host));
    }
    
    public ArrayList<Conection> getPeers() {
        return peers;
    }    
    
    
    
    /*
    
    public void conectarServidor(String ip, int host) {
        servidor = new Conexao(tratamento, ip, host);
        tcIO = new ThreadConections(servidor.getConectionIO());
        new Thread(tcIO).start();
    }

    */

}