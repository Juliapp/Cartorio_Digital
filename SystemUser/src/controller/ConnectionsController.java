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
     */    
    public void initializeUserPeer(int port){
        try {
            userPeer = new UserPeer(port);
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        }
    }
    
    public int getUserPeerPort(){
        return userPeer.getPort();
    }
    
    public String getUserHost(){
        return userPeer.getHost();
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
     *Cria a conexão com o cartório
     * @param host
     * @param port
     * @param askConectionToServer
     */
    public void conectCourtHouse(String host, int port, String askConectionToServer) {
        Peer p = new Peer(host, port);
        p.conect();
        othersPeers.ConectServer(p);
        threadPeer.UpdatePeers(othersPeers);
        threadPeer.sendMessageToCourthouse(askConectionToServer);
    }
    
    /**
     *Adiciona cria um novo peer e o conecta
     * @param host
     * @param port
     * @param askConection
     * @return peer
     */    
    public Peer addPeer(String host, int port, String askConection){
        Peer p = new Peer(host, port);
        p.conect();
        othersPeers.addPeer(p);
        threadPeer.sendMessage(askConection, host, port);
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
        threadPeer.sendMessage(message, host, port);
    }
 
     /**
     *Mansa mensagem para p cartório
     * @param message
     */    
    public void sendMessageToCourthouse(String message) {
        threadPeer.sendMessageToCourthouse(message);
    }  
    
    /**
     *Adiciona cria um novo peer e o conecta
     * @param host
     * @param port
     */
    public void addPeer(String host, int port) {
        Peer p = new Peer(host, port);
        p.conect();
        othersPeers.addPeer(p);
    }
}