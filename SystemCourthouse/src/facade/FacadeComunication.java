package facade;

import controller.ConnectionsController;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 *Facade de conexões
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
     * inicializa o cartório 
     * @param port
     * @throws UnknownHostException
     */
    public void initializeCourtHouse(int port) throws UnknownHostException{
        connectionsController.initializeUserPeer(port);
        connectionsController.initializeThreads();
    }
    
    /**
     *Cria uma nova conexão
     * @param ip
     * @param host
     */
    public void createNewPeerConection(String ip, int host) {
       connectionsController.addPeer(ip, host);
    }
    
    /**
     *Envia uma mensagem
     * @param message
     * @param host
     * @param port
     */
    public void sendMessage(String message, String host, int port){
        connectionsController.sendMessage(message, host, port);
    }
    
}
