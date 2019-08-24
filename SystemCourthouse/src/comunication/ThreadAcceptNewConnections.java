package comunication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *Thread que aceita novas conexões e as devolve para a ThreadUserPeer
 * @author Juliana
 */
public class ThreadAcceptNewConnections extends Thread{
   private final ServerSocket serverSocket;
    private final ThreadUserPeer threadUserPeer;

    public ThreadAcceptNewConnections(ServerSocket serverSocket, ThreadUserPeer threadUserPeer) {
        this.serverSocket = serverSocket;
        this.threadUserPeer = threadUserPeer;
    }

    /**
     *Aguarda por novas conexões e as introduz na ThreadUserPeer
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket sock = serverSocket.accept();
                threadUserPeer.newConectionAcepted(sock.getInputStream());
            } catch (IOException ex) {
                System.err.println(ex);
            }

        }
    }
}
