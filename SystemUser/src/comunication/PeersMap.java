package comunication;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PeersMap {
    private final Map<String, Peer> peersMap;  
    
    public PeersMap() {
        peersMap = new HashMap();
    }    
    
    public Peer addPeer(Peer p) {
       return peersMap.putIfAbsent(p.getIp() + ":" + String.valueOf(p.getPort()), p);
    }
    
    public Peer getPeer(String key){
        return peersMap.get(key);
    }
       
    public Iterator<String> getAllIps(){
        return peersMap.keySet().iterator();
    }
    
    public Iterator<Peer> getIterPeers(){
        return peersMap.values().iterator();
    }

}
