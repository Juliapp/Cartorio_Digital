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
import model.RealtyPassManager;
import model.UserData;

public class FacadeBack {
    
    private UserData user;
    private static FacadeBack facade;
    private final DataCheck datacheck;
    private final ManagerSecurity managerSecurity;
    private RealtyPassManager realtyPassManager;
    
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
        try {
            realtyPassManager = new RealtyPassManager(user.getPuKey());
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }
    
    public UserData getUser(){
        return user;
    }

    public List<Integer> getUserRealties() {
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
    
    public PrivateKey decodePrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException{
        return managerSecurity.decodePrivateKey(privateKey);
    }
    
    public Integer sighDocument(String sellerPublicKey, String privateKey, int rId) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, InvalidKeySpecException{
        return managerSecurity.sighDocument(sellerPublicKey, privateKey, rId);
    }

    public int addNewRealty(int aInt, String publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, InvalidKeySpecException {
        return sighDocument(publicKey, user.getPrKey(), aInt);
    }

    public Integer sighRepassDocument() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, InvalidKeySpecException{
        return managerSecurity.sighDocument(realtyPassManager, user.getPrKey());
    }    

    public RealtyPassManager getPassManager(){
        return realtyPassManager;
    }
    
    public String createRandomCodeNSend(String host, int port) {
        return realtyPassManager.createRandomCode(host, port);
    }

    public void setPassword(String passPassword) {
        realtyPassManager.setPassPassword(passPassword);
    }
    
    public void setRealtyToPass(int realty) {
        realtyPassManager.setRealty(realty);
    }
   
    public void sendRealty(String host, int port){
        realtyPassManager.sendRealty(host, port);
    }    

    public String getSellerhost() {
        return realtyPassManager.getSellerhost();
    }

    public void setSellerhost(String sellerhost) {
        realtyPassManager.setSellerhost(sellerhost);
    }

    public int getSellerport() {
        return realtyPassManager.getSellerport();
    }

    public void setSellerport(int sellerport) {
        realtyPassManager.setSellerport(sellerport);
    }    
    
    public String getPublicKey() {
        return realtyPassManager.getPublicKey();
    }

    public void setPublicKey(String publicKey) {
        realtyPassManager.setPublicKey(publicKey);
    }
    
    public String getSellerPassword(){
        return realtyPassManager.getPassPassword();
    }
}