package comunication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PeersMap {
    private final Map<String, Connection> peersMap;  
    
    public PeersMap() {
        peersMap = new HashMap();
    }    
    
    public Connection createNewPeerConection(String ip, int host) {
       return peersMap.put(ip, new Connection(ip, host));
    }
    
    public Iterator<String> getAllIps(){
        return peersMap.keySet().iterator();
    }
    
    public Iterator<Connection> getAllConnections(){
        return peersMap.values().iterator();
    }
    
    public Iterator<ConnectionIO> getAllConnectionsIO(){
        List<ConnectionIO> cIOList = new ArrayList();
        Iterator aux = getAllConnections();
        while(aux.hasNext()){
            Connection c = (Connection) aux.next();
            cIOList.add(c.getConectionIO());
        }
        return cIOList.iterator();
    }
}
