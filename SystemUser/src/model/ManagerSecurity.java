package model;

import JPAPersistence.DAO;
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
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(cript.BASE64decode(publicKey));
        return keyFactory.generatePublic(publicKeySpec);           
    }
    
    public PrivateKey decodePrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException{
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(cript.BASE64decode(privateKey));
        return keyFactory.generatePrivate(privateKeySpec);           
    }

    public String encodePrivateKey(PrivateKey privateKey){
        return cript.BASE64encode(privateKey.getEncoded());
    }       
    
    public Integer sighDocument(String sellerPublicKey, String privateKey, int rId) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException{
        Realty realty = dao.findRealty(rId);
        PublicKey sellerPK = decodePublicKey(sellerPublicKey);
        if(checkDocument(sellerPK, realty)){
            return sighDocument(sellerPublicKey, realty);
        }
        return 0;
    }
    
    
    public Integer sighDocument(String strBuyerPrivateKey, Realty realty) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, InvalidKeySpecException{
        PrivateKey privateKey = decodePrivateKey(strBuyerPrivateKey);
        //Initializing a new signature
        Signature signature = Signature.getInstance(ALGORITHM);
        signature.initSign(privateKey);
        //Gerate new Random Hash value and convert to String
        String newHash = Integer.toHexString(ThreadLocalRandom.current().nextInt());
        //Concat the house charter with the new hash
        String docNHashToSigh = signable(realty, newHash);
        //prepering to sigh
        signature.update(cript.BASE64decode(docNHashToSigh));
        //gerate the new signature
        byte[] docSigh = signature.sign();
        //update the new sign and the new hash
        realty.mergeNewSignature(docSigh, newHash);
        //update realty in the database
        realty = dao.mergeRealty(realty);
        //return the number of the realtyId to be added to the new owner
        return realty != null ? realty.getId() : 0;
    }
    
    public boolean checkDocument(PublicKey sellerPublicKey, Realty realty) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException{
        Signature sellerSig = Signature.getInstance(ALGORITHM);
        sellerSig.initVerify(sellerPublicKey);
        //the realty's hash is checked in the database, and garanties that is not an old hashcode
        sellerSig.update(cript.BASE64decode(signable(realty, realty.getHash())));
        return sellerSig.verify(realty.getSignature());
    }

    public Integer sighDocument(RealtyPassManager realtyPassManager, String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        return sighDocument(realtyPassManager.getPublicKey(), privateKey, realtyPassManager.getRealty());
    }
    
    public String signable(Realty realty, String hash){
        if(realty.getHouseCharter().length() >= 50 ){
            return realty.getHouseCharter().substring(0, 49).concat(hash);
        }else{
            return realty.getHouseCharter().concat(hash);
        }
    }
}
