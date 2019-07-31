package facade;

import java.io.FileNotFoundException;
import java.io.IOException;
import model.DataProcess;

public class FacadeBack {
    private final DataProcess dataProsses;
    
    private static FacadeBack facade;
    
    public static synchronized FacadeBack getInstance() throws IOException, FileNotFoundException, ClassNotFoundException {
        return (facade == null) ? facade = new FacadeBack(): facade;
    }        

    public FacadeBack() {
        dataProsses = new DataProcess();
    }

    public DataProcess getDataProsses() {
        return dataProsses;
    }
    
    public void put(byte[] inputedBytes){
        dataProsses.pullMessange(inputedBytes);
    }
    
    

//    public void conectarServidor(String ip, int host) {
//        
//        c.createNewPeerConection(ip, host);
//        servidor = new Conexao(tratamento, ip, host);
//        tcIO = new ThreadConections(servidor.getConectionIO());
//        new Thread(tcIO).start();
//    }

}
