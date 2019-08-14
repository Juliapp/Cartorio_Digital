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
import model.UserData;
import org.json.JSONObject;
import util.Cript;


public class SecurityManagerController {
    private final ManagerSecurity manager;
    private final Cript cript;

    public SecurityManagerController() {
        manager = new ManagerSecurity();
        cript = new Cript();
    }
    
    public Integer signDocument(String courtPK, JSONObject realtyJson) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException{
        Realty realty = new Realty();
        realty.setAddress(realtyJson.getString("address"));
        realty.setHouseCharter(cript.BASE64decode(cript.UTF8BASE64converter(realtyJson.getString("charter"))));
        return manager.sighDocument(courtPK, realty);
    }
    
    public String encodePublicKey(PublicKey publicKey){
        return manager.encodePublicKey(publicKey);
    }
    
    public String encodePrivateKey(PrivateKey privateKey){
        return manager.encodePrivateKey(privateKey);
    }
    
    public KeyPair DSAkeyPairGenerator() throws NoSuchAlgorithmException{
        return manager.DSAkeyPairGenerator();
    }
    
}
