package model;

import JPAPersistence.DAO;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class ManagerSecurity {
    private final DAO dao;

    public ManagerSecurity() {
        this.dao = new DAO();
    }
    
    public KeyPair DSAkeyPairGenerator() throws NoSuchAlgorithmException{
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
        SecureRandom sr = new SecureRandom();
        kpg.initialize(512, sr);
        return kpg.generateKeyPair();
    }
    
    public String encodePublicKey(PublicKey publicKey){
        return Arrays.toString(publicKey.getEncoded());
    }

    public PublicKey decodePublicKey(String publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] publicKeyBytes = publicKey.getBytes(StandardCharsets.UTF_8);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        return keyFactory.generatePublic(publicKeySpec);           
    }
    
    public Realty sighDocument(PrivateKey privateKey, Realty realty) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException{
        //Initializing a new signature
        Signature signature = Signature.getInstance("DSA");
        signature.initSign(privateKey);
        //Gerate new Random Hash value
        byte[] newHash = intToByteArray(ThreadLocalRandom.current().nextInt());       
        //Concat the house charter with the new hash
        byte[] docNHashToSigh = concatByteArrays(realty.getHouseCharter(), newHash);
        signature.update(docNHashToSigh);
        //gerate the new signature
        byte[] docSigh = signature.sign();
        //update the new sign and the new hash
        realty.mergeNewSignature(docSigh, newHash);
        //update them in the database
        dao.saveRealty(realty);
        //return the number of the realtyId to be added to the new owner
        return realty;
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
