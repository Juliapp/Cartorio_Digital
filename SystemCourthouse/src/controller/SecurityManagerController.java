package controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import model.ManagerSecurity;
import model.Realty;
import model.UserData;
import org.json.JSONObject;


public class SecurityManagerController {
    private final ManagerSecurity manager;
    private final UserData courtReference;
    

    public SecurityManagerController(UserData court) {
        manager = new ManagerSecurity();
        courtReference = court;
    }
    
    public Realty signDocument(JSONObject realtyJson) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException{
        Realty realty = new Realty();
        realty.setAddress(realtyJson.getString("address"));
        realty.setHouseCharter(realtyJson.getString("charter").getBytes(StandardCharsets.UTF_8));
        return manager.sighDocument(courtReference.getPrKey(), realty);
    }
    
    public String encodePublicKey(){
        return manager.encodePublicKey(courtReference.getPuKey());
    }
    
    
}
