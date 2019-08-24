package comunication;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *Mapa com todos os peers que esse sistema se conectou
 * @author Juliana
 */
public class PeersMap {
    private final Map<String, Peer> peersMap;  
    
    
    public PeersMap() {
        peersMap = new HashMap();
    }    
    
    /**
     *Adiciona um novo peer ao mapa de peers, tendo como chave o nome de seu host contatenado
     * ao nome de sua porta caso ele já não exista no mapa
     * @param peer
     * @return peer
     */
    
    public Peer addPeer(Peer peer) {
       return peersMap.putIfAbsent(peer.getIp() + ":" + String.valueOf(peer.getPort()), peer);
    }
    
    /**
     *Guarda a informação do peer cartório
     * @param p
     * @return
     */
    public Peer ConectServer(Peer p){
        Peer pe = peersMap.putIfAbsent("courthouse", p);        
        return pe;
    }
    
    /**
     *Pega a referência do peer a partir de sua chave
     * @param key
     * @return peer
     */
    public Peer getPeer(String key){
        return peersMap.get(key);
    }
       
    
    /**
     *
     * @return Iterador com todos os peers
     */
    public Iterator<Peer> getIterPeers(){
        return peersMap.values().iterator();
    }

    /**
     *Pega um peer específico
     * @param host
     * @param port
     * @return peer
     */
    public Peer getPeer(String host, int port) {
        return peersMap.get(host + ":" + String.valueOf(port));
    }
    
    /**
     *Pega a referência do peer cartório
     * @return peer cartório
     */
    public Peer getCourthouse(){
        return peersMap.get("courthouse");
    }    

}
