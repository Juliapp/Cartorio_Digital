package controller;

import comunication.Peer;
import facade.FacadeComunication;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Realty;

public class PassSignatureController {
    private Realty reatyToPass;
    private Peer peer;
    
    private FacadeComunication facadec;
    
    public PassSignatureController(Realty realty){
        reatyToPass = realty;
        
        try {
            facadec = FacadeComunication.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(PassSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertPeer(String host, int port){
        
    }
    
}
