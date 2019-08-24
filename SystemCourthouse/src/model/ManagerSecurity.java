package model;

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

/**
 *
 * @author Juliana
 */
public class ManagerSecurity {
    //Codificação
    private final Cript cript;
    //Algorítimo de segurança
    private final String ALGORITHM = "DSA";

    /**
     *
     */
    public ManagerSecurity() {
        cript = new Cript();
    }
    
    /**
     *Gera um novo par de chaves DSA
     * @return
     * @throws NoSuchAlgorithmException
     */
    public KeyPair DSAkeyPairGenerator() throws NoSuchAlgorithmException{
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
        SecureRandom sr = new SecureRandom();
        kpg.initialize(512, sr);
        return kpg.generateKeyPair();
    }
    
    /**
     *encode na chave pública (PublicKey para String)
     * @param publicKey
     * @return
     */
    public String encodePublicKey(PublicKey publicKey){
        return cript.BASE64encode(publicKey.getEncoded());
    }

    /**
     *decode na chave pública (String para PublicKey)
     * @param publicKey
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public PublicKey decodePublicKey(String publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        byte[] key = cript.BASE64decode(publicKey);
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(key);
        return keyFactory.generatePublic(publicKeySpec);           
    }
    
    /**
     *decoda a chave privada (String para PrivateKey)
     * @param privateKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public PrivateKey decodePrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException{
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        byte[] key = cript.BASE64decode(privateKey);
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(key);
        keyFactory.generatePrivate(privateKeySpec);
        return keyFactory.generatePrivate(privateKeySpec);           
    }
    
    /**
     *Encode na chave privada (PrivateKey para String)
     * @param privateKey
     * @return
     */
    public String encodePrivateKey(PrivateKey privateKey){
        return cript.BASE64encode(privateKey.getEncoded());
    }    
    
    /**
     *Assina o documento
     * @param strBuyerPrivateKey
     * @param realty
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws IOException
     * @throws InvalidKeySpecException
     */
    public Realty sighDocument(String strBuyerPrivateKey, Realty realty) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, InvalidKeySpecException{
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
        return realty;
    }
    
    /**
     *Torna o documento assinável, pegando os 60 primeiros caracteres da escritura junto com sua hash atual
     * @param realty
     * @param hash
     * @return
     */
    public String signable(Realty realty, String hash){
        if(realty.getHouseCharter().length() >= 60 ){
            return realty.getHouseCharter().substring(0, 59).concat(hash);
        }else{
            return realty.getHouseCharter().concat(hash);
        }
    }    
}
