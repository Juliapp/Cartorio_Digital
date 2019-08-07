package comunication;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public Peer getPeer(String host, int port) {
        try {
            if(host.equals(InetAddress.getLocalHost().getHostAddress())){
                return peersMap.get("localhost" + ":" + String.valueOf(port));
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(PeersMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return peersMap.get(host + ":" + String.valueOf(port));
    }
    
}
