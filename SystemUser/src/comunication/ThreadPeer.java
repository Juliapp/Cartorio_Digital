package comunication;

import java.util.Iterator;
import util.Cript;

/**
 *Thread que cuida do envio de mensagens 
 * @author Juliana
 */

public class ThreadPeer extends Thread{
    //Referência ao mapa de peers
    private PeersMap peers;

    //True se tem mensagem para ser enviada
    private boolean hasMessageToSend;
    //True se essa mensagem para ser enviada é para todos os peers
    //dessa forma fica opcional fazer um multicast
    private boolean isMessageToAll;
    //referência global de um peer 
    private Peer aux;
    //buffer da mensagem a ser enviada 
    private String bufferedMessage;

    //monitor de thread
    private final PC pc;
    //encoder
    private final Cript cript;
    
    public ThreadPeer(){
        hasMessageToSend = false;
        pc = new PC();
        cript = new Cript();
    }
    
    /**
     *Thread para mandar mensagens em ação. Ela faz a thread esperar até segunda ordem,
     *atravez dos métodos para enviar mensagem nesse própio objeto thread. Se a mensagem 
     * for para todos os peers ele itera o map de peers e caso seja só para um ele pega a 
     * referência global da classe e envia para este
     */    
    
    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                pc.makeThreadWait();
                if(hasMessageToSend){
                    if(isMessageToAll){
                        Iterator<Peer> ip = peers.getIterPeers();
                        while(ip.hasNext()){
                            ip.next().send(convertToByte(bufferedMessage));
                        }    
                        hasMessageToSend = false;
                    }
                    else{
                        aux.send(convertToByte(bufferedMessage));
                        hasMessageToSend = false;
                    }
                }
            }catch(InterruptedException ex){
                System.err.println(ex);
                Thread.currentThread().interrupt();
            }
        }
    }

     /*Envia a mensagem para todos os peers
     * @param message
     */
    void sendMessageToAll(String message) {
        bufferedMessage = message;
        hasMessageToSend = true;
        isMessageToAll = true;
        try {
            pc.makeThreadWakeUp();
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }

    /**
     *atualiza o mapa de peers
     * @param peers
     */    
    public void UpdatePeers(PeersMap peers) {
        this.peers = peers;
    }

     /**
     *Envia a mensageme somente a um peer passado por referência. Como não podemos modificar 
     * uma thread em curso, setamos uma referência global que está como atributo desta classe
     * para fazer a passagem de mensagem
     * 
     * @param message
     * @param host
     * @param port
     */
    public void sendMessage(String message, String host, int port) {
        aux = peers.getPeer(host, port);
        bufferedMessage = message;
        hasMessageToSend = true;
        isMessageToAll = false;
        try {
            pc.makeThreadWakeUp();
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }

     /**
     *Envia mensagem ao cartório
     * @param message
     */    
    public void sendMessageToCourthouse(String message) {
        aux = peers.getCourthouse();
        bufferedMessage = message;
        hasMessageToSend = true;
        isMessageToAll = false;
        try {
            pc.makeThreadWakeUp();
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }    
    
    /**
     *Converte a mensagem em string para bytes
     * @param string
     * @return
     */    
    
    public byte[] convertToByte(String string) {
        return cript.UTF8decode(string);
    }
    
    /*
    *Monitor de thread. Este objeto é um artifício para obrigar a thread esperar
    *e ser notificada de acordo com a demanda de mensagem
    */    
    private static class PC{
        public void makeThreadWait() throws InterruptedException {
            synchronized (this) {
                wait();
            }
        }

        public void makeThreadWakeUp() throws InterruptedException {
            synchronized (this) {
                notify();
            }
        }        
    }
}
