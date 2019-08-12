package facade;


import model.DataCheck;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;
import model.ManagerSecurity;
import model.ObserverData;
import model.Realty;
import model.UserData;

public class FacadeBack {
    
    private UserData user;
    private static FacadeBack facade;
    private final DataCheck datacheck;
    private final ManagerSecurity managerSecurity;
    
    public static synchronized FacadeBack getInstance() throws IOException, ClassNotFoundException {
        return (facade == null) ? facade = new FacadeBack(): facade;
    }        

    public FacadeBack() {
        datacheck = new DataCheck();
        managerSecurity = new ManagerSecurity();
    }
    
    public void checkTrue(){
        datacheck.setSucessfullLogin(true);
    }
    
    public void checkFalse(){
        datacheck.setSucessfullLogin(false);
    }
    
    public DataCheck getDataCheck(){
        return datacheck;
    }
    
    public void addObservable(ObserverData observer){
        datacheck.addObserver(observer);
    }
    

    public void initializeUser(UserData user){
        this.user = user;
    }
    
    public UserData getUser(){
        return user;
    }

    public List<Realty> getUserRealties() {
        return user.getRealties();
    }
    
    //SECURITY MANAGER
    
    public KeyPair DSAkeyPairGenerator() throws NoSuchAlgorithmException{
        return managerSecurity.DSAkeyPairGenerator();
    }
    
    public String encodePublicKey(PublicKey publicKey){
        return Arrays.toString(publicKey.getEncoded());
    }

    public PublicKey decodePublicKey(String publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return managerSecurity.decodePublicKey(publicKey);
    }
    
    public Integer sighDocument(PrivateKey privateKey, Realty realty) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException{
        return managerSecurity.sighDocument(privateKey, realty);
    }
    
    public boolean checkDocument(PublicKey sellerPublicKey, Realty realty) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException{
        return managerSecurity.checkDocument(sellerPublicKey, realty);
    }    

}