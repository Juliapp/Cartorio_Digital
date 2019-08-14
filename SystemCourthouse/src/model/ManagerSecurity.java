package model;

import JPAPersistence.DAO;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.concurrent.ThreadLocalRandom;
import util.Cript;

public class ManagerSecurity {
    private final DAO dao;
    private final Cript cript;
    private final String ALGORITHM = "DSA";

    public ManagerSecurity() {
        this.dao = new DAO();
        cript = new Cript();
    }
    
    public KeyPair DSAkeyPairGenerator() throws NoSuchAlgorithmException{
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
        SecureRandom sr = new SecureRandom();
        kpg.initialize(512, sr);
        return kpg.generateKeyPair();
    }
    
    public String encodePublicKey(PublicKey publicKey){
        return cript.BASE64encode(publicKey.getEncoded());
    }

    public PublicKey decodePublicKey(String publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        byte[] key = cript.BASE64decode(publicKey);
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(key);
        return keyFactory.generatePublic(publicKeySpec);           
    }
    
    public PrivateKey decodePrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException{
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        byte[] key = cript.BASE64decode(privateKey);
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(key);
        keyFactory.generatePrivate(privateKeySpec);
        return keyFactory.generatePrivate(privateKeySpec);           
    }
    
    public String encodePrivateKey(PrivateKey privateKey){
        return cript.BASE64encode(privateKey.getEncoded());
    }    
    
    public Integer sighDocument(String privateKey, Realty realty) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, InvalidKeySpecException{
        PrivateKey courtPrKey = decodePrivateKey(privateKey);
        //Initializing a new signature
        Signature signature = Signature.getInstance(ALGORITHM);
        signature.initSign(courtPrKey);
        //Gerate new Random Hash value
        byte[] newHash = intToByteArray(ThreadLocalRandom.current().nextInt());       
        //Concat the house charter with the new hash
        System.out.println(newHash);
        
        byte[] docNHashToSigh = concatByteArrays(realty.getHouseCharter(), newHash);
        
        signature.update(docNHashToSigh);
        //gerate the new signature
        byte[] docSigh = signature.sign();
        //update the new sign and the new hash
        realty.mergeNewSignature(docSigh, newHash);
        //update them in the database
        realty = dao.saveRealty(realty);
        //return the number of the realtyId to be added to the new owner
        return realty != null ? realty.getId() : 0;
    }
    
    private byte[] intToByteArray (int i) throws IOException {      
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeInt(i);
        dos.flush();
        return bos.toByteArray();
    }    
    
    private byte[] concatByteArrays(byte[] charter, byte[] hash) throws IOException{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write(charter);
        outputStream.write(hash);
        return outputStream.toByteArray();
    }
}
