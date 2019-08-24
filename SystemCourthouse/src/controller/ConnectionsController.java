package controller;

import comunication.Peer;
import comunication.PeersMap;
import comunication.ThreadPeer;
import comunication.ThreadUserPeer;
import comunication.UserPeer;
import java.net.UnknownHostException;

/**
 *Controlador de conexões
 * @author Juliana
 */
public class ConnectionsController{
    //Referência para o peer atual
    private UserPeer userPeer;
    //Mapa de peer que se conectaram com ele 
    private final PeersMap othersPeers;
    //Thread de inputs
    private ThreadUserPeer threadUserPeer;
    //Thread de outputs
    private ThreadPeer threadPeer;
    
    public ConnectionsController() {
        othersPeers = new PeersMap();
    }
    
    /**
     *Inicializa o peer usuário pela primeira vez
     * @param port
     * @throws UnknownHostException
     */
    public void initializeUserPeer(int port) throws UnknownHostException{
        if(userPeer == null){
            userPeer = new UserPeer(port);
        }
    }
    
    /**
     *Inicialização das threads
     */
    public void initializeThreads(){
        threadUserPeer = new ThreadUserPeer(userPeer);
        new Thread(threadUserPeer).start();          
        threadPeer = new ThreadPeer();
        new Thread(threadPeer).start();
    }
    
    /**
     *Adiciona cria um novo peer e o conecta
     * @param host
     * @param port
     * @return
     */
    public Peer addPeer(String host, int port){
        Peer p = new Peer(host, port);
        p.conect();
        othersPeers.addPeer(p);
        return p;
    }
    
    /**
     *Pega o peer a partir de sua chave
     * @param key
     * @return
     */
    public Peer getPeer(String key){
        return othersPeers.getPeer(key);
    }

    /**
     *Mansa mensagem para tal peer
     * @param message
     * @param host
     * @param port
     */
    public void sendMessage(String message, String host, int port) {
        threadPeer.sendMessage(message, othersPeers.getPeer(host, port));
    }
    
}