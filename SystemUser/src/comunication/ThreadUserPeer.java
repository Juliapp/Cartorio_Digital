package comunication;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.DataProcess;

/**
 *Thread responsável pelo recebimento de mensagens
 * @author Juliana
 */
public class ThreadUserPeer extends Thread {
    //Referência para o usuário
    private final UserPeer userPeer;
    //Lista de inputStreams geradas a partir do AcceptConnections
    private final List<InputStream> inputs;
    //array de bytes vazio para evitar criar desnecessariamentes novos objetos
    private final byte[] emptyByteArrayReference;
    //referência para o array de bytes recebidos
    private byte[] reciveReference;
    //Classe para processamento de dados recebidos por essa thread
    private final DataProcess dataProcess;


    public ThreadUserPeer(UserPeer userPeer) {
        inputs = new ArrayList<>();
        this.userPeer = userPeer;
        emptyByteArrayReference = new byte[]{};
        dataProcess = new DataProcess();
    }

    /**
     *Método para receber as mensagens
     */
    @Override
    public void run() {
        //conecta a primera vez este peer (Com o cartório)
        userPeer.conect();
        //Adiciona no array de inputs
        inputs.add(userPeer.getInputStream());
        //Faz rodar a thread responsável pelas novas conexões
        new ThreadAcceptNewConnections(userPeer.getServerSocket(), this).start();

        
        //Verifica a todo tempo caso chegou mensagem 
        //esse método não é o mais rentável para esse tipo de aplicação, mas não apresentou 
        //tanto consumo de memória enquanto rodava
        while (!Thread.currentThread().isInterrupted()) {
            //Método sincronizado para controlar o acesso aos inputs
            synchronized (inputs) {
                try {
                    pullMessage();
                } catch (ClassNotFoundException | SQLException ex) {
                    System.err.println(ex);
                }
            }
        }
    }

    //itera o array de inputs e verifica se houve mensagem
    private synchronized void pullMessage() throws ClassNotFoundException, SQLException {
        for (InputStream input : inputs) {
            reciveReference = toByteArray(input);
            if (reciveReference.length > 0) {
                dataProcess.pullMessage(reciveReference);
            }
        }
    }

    
    //transforma o input em um array de bytes
    private byte[] toByteArray(InputStream input) {
        DataInputStream dataInputStream = new DataInputStream(input);

        byte buffer[] = emptyByteArrayReference;
        try {
            if (dataInputStream.available() > 0) {
                buffer = new byte[dataInputStream.available()];
                dataInputStream.readFully(buffer);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return buffer;
    }

    /**
     *Esse método é utilizado pelo ThreadAccept para aceitar novas conexçoes e jogar no array de inputs
     * @param input
     * @throws IOException
     */
    public void newConectionAcepted(InputStream input) throws IOException {
        //Método sincronizado para controlar o acesso aos inputs
        synchronized(inputs){
            inputs.add(input);
        }
    }
    
}
