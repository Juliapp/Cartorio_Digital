package model;

import facade.FacadeComunication;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.stream.Collectors;
import org.json.JSONObject;

public class RealtyPassManager {
    private int realty;
    private String passPassword;
    private String publicKey;
    private final FacadeComunication facadec;
    
    private String sellerhost;
    private int sellerport;

    public RealtyPassManager(String publicKey) throws IOException, ClassNotFoundException {
        this.publicKey = publicKey;
        facadec = FacadeComunication.getInstance();
    }
    
    public String createRandomCode(String host, int port) {
        String id = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        int codeLength = 6;
        String ran = new SecureRandom()
                .ints(codeLength, 0, id.length())
                .mapToObj(id::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
        this.passPassword = ran;
        sendRealty(host,  port);
        return ran;
    }
    
    

    public int getRealty() {
        return realty;
    }

    public void setRealty(int realty) {
        this.realty = realty;
    }

    public String getPassPassword() {
        return passPassword;
    }

    public void setPassPassword(String passPassword) {
        this.passPassword = passPassword;
    }

    public String getSellerhost() {
        return sellerhost;
    }

    public void setSellerhost(String sellerhost) {
        this.sellerhost = sellerhost;
    }

    public int getSellerport() {
        return sellerport;
    }

    public void setSellerport(int sellerport) {
        this.sellerport = sellerport;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
    
    
   
    public void sendRealty(String host, int port){
        JSONObject reply = new JSONObject();
        reply.accumulate("reply", "Repassing Realty");
        reply.accumulate("password", passPassword);
        reply.accumulate("rId", realty);
        reply.accumulate("publicKey", publicKey);
        reply.accumulate("host", facadec.getUserHost());
        reply.accumulate("port", facadec.getUserPeerPort());
        facadec.sendMessage(reply.toString(), host, port);
    }
    
    
}
