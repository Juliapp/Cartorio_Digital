package facade;

import controller.ConnectionsController;
import java.io.IOException;

/**
 *
 * @author Juliana
 */
public class FacadeComunication {
    private final ConnectionsController connectionsController;
    private static FacadeComunication facade;
    
    private FacadeComunication(){
        connectionsController = new ConnectionsController();
    }
    
    /**
     *Padrão singleton
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static synchronized FacadeComunication getInstance() throws IOException, ClassNotFoundException {
        return (facade == null) ? facade = new FacadeComunication(): facade;
    }    
    
    /**
     *Inicializa esse usuário
     * @param port
     */
    public void initializeUserPeer(int port){
        connectionsController.initializeUserPeer(port);
        connectionsController.initializeThreads();
    }
    
    /**
     *
     * @return
     */
    public int getUserPeerPort(){
        return connectionsController.getUserPeerPort();
    }    
    
    /**
     *
     * @return
     */
    public String getUserHost(){
        return connectionsController.getUserHost();
    }
    
    /**
     *Cria um peer e pede pra ele se conectar a você 
     * @param ip
     * @param host
     * @param askConection
     */
    public void createNewPeerConection(String ip, int host, String askConection) {
       connectionsController.addPeer(ip, host, askConection);
    }
    
    /**
     *Cria um novo peer
     * @param host
     * @param port
     */
    public void createNewPeerConection(String host, int port) {
       connectionsController.addPeer(host, port);
    }
    
    /**
     *Manda mensagem para algum peer 
     * @param message
     * @param host
     * @param port
     */
    public void sendMessage(String message, String host, int port){
        connectionsController.sendMessage(message, host, port);
    }
    
    /**
     *Manda uma mensagem ao cartório
     * @param message
     */
    public void sendMessageToCourthouse(String message){
        connectionsController.sendMessageToCourthouse(message);
    }

    /**
     *Conecta com o cartório
     * @param ip
     * @param port
     * @param askConectionToServer
     */
    public void conectServer(String ip, int port, String askConectionToServer) {
        connectionsController.conectCourtHouse(ip, port, askConectionToServer); 
    }
}
