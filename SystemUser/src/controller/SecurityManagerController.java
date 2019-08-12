package controller;

import facade.FacadeBack;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import model.Realty;
import model.UserData;


public class SecurityManagerController {
    private FacadeBack facadeb;

    public SecurityManagerController() {
        try {
            facadeb = FacadeBack.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);   
        }
    }
    
    public void signDocument(Realty realty, String pkey) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException{
        UserData user = facadeb.getUser();
        PublicKey sellerPublicKey = facadeb.decodePublicKey(pkey);
        if(facadeb.checkDocument(sellerPublicKey, realty)){
            user.addRealty(facadeb.sighDocument(user.getPrKey(), realty));
        }
        //soltar um poopup
    }
    
    
}
