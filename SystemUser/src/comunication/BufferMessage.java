package comunication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BufferMessage{
    private byte[] toSend;
    private final List<byte[]> toRecive;
    
    private static BufferMessage bufferMessage;
    
    private BufferMessage(){
        toSend = new byte[]{};
        toRecive = new ArrayList();
    }
    
    public static synchronized BufferMessage getInstance() throws IOException, ClassNotFoundException {
        return (bufferMessage == null) ? bufferMessage = new BufferMessage(): bufferMessage;
    }        

    public byte[] getToSend() {
        return toSend;
    }

    public void setToSend(byte[] toSend) {
        this.toSend = toSend;
    }
    
    public void addMessageToRecive(byte[] newMessage){
        toRecive.add(newMessage);
    }
    
    public List<byte[]> getMessagesToTreat(){
        List<byte[]> aux = toRecive;
        toRecive.removeAll(toRecive);
        return aux;
    }
    
    
}
