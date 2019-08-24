package facade;

import controller.DaoController;
import model.DataCheck;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import model.ManagerSecurity;
import model.ObserverData;
import model.Realty;
import model.RealtyPassManager;
import model.UserData;

/**
 *
 * @author Juliana
 */
public class FacadeBack {
    
    private UserData user;
    private static FacadeBack facade;
    private final DataCheck datacheck;
    private final ManagerSecurity managerSecurity;
    private RealtyPassManager realtyPassManager;
    private final DaoController dao;
    
    /**
     *Padrão singleton
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static synchronized FacadeBack getInstance() throws IOException, ClassNotFoundException {
        return (facade == null) ? facade = new FacadeBack(): facade;
    }        

    /**
     *
     */
    public FacadeBack() {
        dao = new DaoController();
        datacheck = new DataCheck();
        managerSecurity = new ManagerSecurity();
    }
    
    /**
     *Seta o login feito com sucesso para true para a passagem da página
     */
    public void checkTrue(){
        datacheck.setSucessfullLogin(true);
    }
    
    /**
     *Seta o login feito com sucesso para false para permanecer na mesma página
     */
    public void checkFalse(){
        datacheck.setSucessfullLogin(false);
    }
    
    /**
     *Pega o resultado do login 
     * @return
     */
    public DataCheck getDataCheck(){
        return datacheck;
    }
    
    /**
     *Adiciona um observer para a mudança de cena de login
     * @param observer
     */
    public void addObservable(ObserverData observer){
        datacheck.addObserver(observer);
    }
    
    /**
     * inicializa o usuário
     * @param user
     */
    public void initializeUser(UserData user){
        this.user = user;
        try {
            realtyPassManager = new RealtyPassManager(user.getPuKey());
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }
    
    /**
     *
     * @return
     */
    public UserData getUser(){
        return user;
    }

    /**
     *
     * @return
     */
    public List<Object> getUserRealties() {
        return user.getRealties();
    }
    
    //SECURITY MANAGER

    /**
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    
    public KeyPair DSAkeyPairGenerator() throws NoSuchAlgorithmException{
        return managerSecurity.DSAkeyPairGenerator();
    }
    
    /**
     *
     * @param publicKey
     * @return
     */
    public String encodePublicKey(PublicKey publicKey){
        return Arrays.toString(publicKey.getEncoded());
    }

    /**
     *
     * @param publicKey
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public PublicKey decodePublicKey(String publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return managerSecurity.decodePublicKey(publicKey);
    }
    
    /**
     *
     * @param privateKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public PrivateKey decodePrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException{
        return managerSecurity.decodePrivateKey(privateKey);
    }
    
    /**
     *Assina um documento
     * @param sellerPublicKey
     * @param privateKey
     * @param rId
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws IOException
     * @throws InvalidKeySpecException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Integer sighDocument(String sellerPublicKey, String privateKey, int rId) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, InvalidKeySpecException, ClassNotFoundException, SQLException{
        try{
            Realty realty = getRealty(rId);
            realty = managerSecurity.sighDocument(sellerPublicKey, privateKey, realty);
            realty = merge(realty);
            return realty.getId();
        }catch(NullPointerException ex){
            System.err.println(ex);
            return null;
        }
    }
    
    /**
     *Faz o merge do documento
     * @param realty
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Realty merge(Realty realty) throws ClassNotFoundException, SQLException{
        return dao.mergeRealty(realty);
    }

    /**
     *Adiciona uma nova escritura para ser assinada 
     * @param aInt
     * @param publicKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws IOException
     * @throws InvalidKeySpecException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Integer addNewRealty(int aInt, String publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, InvalidKeySpecException, ClassNotFoundException, SQLException {
        return sighDocument(publicKey, user.getPrKey(), aInt);
    }

    /**
     *Assina um documento repassado
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws IOException
     * @throws InvalidKeySpecException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Realty sighRepassDocument() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, InvalidKeySpecException, ClassNotFoundException, SQLException{
        Realty realty = getRealty(realtyPassManager.getRealty());
        return managerSecurity.sighDocument(realtyPassManager.getPublicKey(), user.getPrKey(), realty);
    }
    
    /**
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Realty getRealty(Integer id) throws ClassNotFoundException, SQLException{
        return dao.findRealty(id);
    }

    /**
     *
     * @return
     */
    public RealtyPassManager getPassManager(){
        return realtyPassManager;
    }
    
    /**
     *
     * @param host
     * @param port
     * @return
     */
    public String createRandomCodeNSend(String host, int port) {
        return realtyPassManager.createRandomCode(host, port);
    }

    /**
     *
     * @param passPassword
     */
    public void setPassword(String passPassword) {
        realtyPassManager.setPassPassword(passPassword);
    }
    
    /**
     *
     * @param realty
     */
    public void setRealtyToPass(int realty) {
        realtyPassManager.setRealty(realty);
    }
   
    /**
     *
     * @param host
     * @param port
     */
    public void sendRealty(String host, int port){
        realtyPassManager.sendRealty(host, port);
    }    

    /**
     *
     * @return
     */
    public String getSellerhost() {
        return realtyPassManager.getSellerhost();
    }

    /**
     *
     * @param sellerhost
     */
    public void setSellerhost(String sellerhost) {
        realtyPassManager.setSellerhost(sellerhost);
    }

    /**
     *
     * @return
     */
    public int getSellerport() {
        return realtyPassManager.getSellerport();
    }

    /**
     *
     * @param sellerport
     */
    public void setSellerport(int sellerport) {
        realtyPassManager.setSellerport(sellerport);
    }    
    
    /**
     *
     * @return
     */
    public String getPublicKey() {
        return realtyPassManager.getPublicKey();
    }

    /**
     *
     * @param publicKey
     */
    public void setPublicKey(String publicKey) {
        realtyPassManager.setPublicKey(publicKey);
    }
    
    /**
     *
     * @return
     */
    public String getSellerPassword(){
        return realtyPassManager.getPassPassword();
    }
}