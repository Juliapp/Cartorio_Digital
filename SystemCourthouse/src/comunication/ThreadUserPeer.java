package comunication;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import controller.OutputedDataProcess;
import java.util.ArrayList;
import java.util.List;

public class ThreadUserPeer extends Thread {

    private final UserPeer userPeer;
    private final List<InputStream> inputs;
    private final byte[] emptyByteArrayReference;
    private byte[] reciveReference;
    private final OutputedDataProcess dataProcess;

    //pega o input com o singleton
    public ThreadUserPeer(UserPeer userPeer) {
        inputs = new ArrayList<>();
        this.userPeer = userPeer;
        emptyByteArrayReference = new byte[]{};
        dataProcess = new OutputedDataProcess();
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void run() {
        userPeer.conect();
        inputs.add(userPeer.getInputStream());
        new ThreadAcceptNewConnections(userPeer.getServerSocket(), this).start();

        while (!Thread.currentThread().isInterrupted()) {
            synchronized (inputs) {
                pullMessage();
            }
        }
    }

    private synchronized void pullMessage() {
        for (InputStream input : inputs) {
            reciveReference = toByteArray(input);
            if (reciveReference.length > 0) {
                dataProcess.pullMessage(reciveReference);
            }
        }
    }

    private byte[] toByteArray(InputStream input) {
        DataInputStream dataInputStream = new DataInputStream(input);

        byte buffer[] = emptyByteArrayReference;
        try {
            if (dataInputStream.available() > 0) {
                buffer = new byte[dataInputStream.available()];
                dataInputStream.readFully(buffer);
            }
        } catch (IOException ex) {

        }
        return buffer;
    }

    public void newConectionAcepted(InputStream input) throws IOException {
        synchronized(inputs){
            inputs.add(input);
        }
    }

}
