package controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import model.ManagerSecurity;
import model.Realty;
import org.json.JSONObject;

/**
 *Controlador do gerenceador de segurança
 * @author Juliana
 */
public class SecurityManagerController {
    private final ManagerSecurity manager;

    public SecurityManagerController() {
        manager = new ManagerSecurity();
    }
    
    /**
     *Assinar documento
     * @param courtPK
     * @param realtyJson
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws IOException
     */
    public Realty signDocument(String courtPK, JSONObject realtyJson) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException{
        Realty realty = new Realty();
        realty.setAddress(realtyJson.getString("address"));
        realty.setHouseCharter(realtyJson.getString("charter"));
        return manager.sighDocument(courtPK, realty);
    }
    
    /**
     *Encodar chave publica para String
     * @param publicKey
     * @return
     */
    public String encodePublicKey(PublicKey publicKey){
        return manager.encodePublicKey(publicKey);
    }
    
    /**
     *Encodar chave privada para String
     * @param privateKey
     * @return
     */
    public String encodePrivateKey(PrivateKey privateKey){
        return manager.encodePrivateKey(privateKey);
    }
    
    /**
     *Gerador de par de chaves DSA
     * @return par de chaves
     * @throws NoSuchAlgorithmException
     */
    public KeyPair DSAkeyPairGenerator() throws NoSuchAlgorithmException{
        return manager.DSAkeyPairGenerator();
    }
    
}
