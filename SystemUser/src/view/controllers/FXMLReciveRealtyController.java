package view.controllers;

import com.jfoenix.controls.JFXTextField;
import facade.FacadeBack;
import facade.FacadeComunication;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.json.JSONObject;

public class FXMLReciveRealtyController implements Initializable {

    @FXML   private JFXTextField tfPassword;
    private FacadeBack facadeb;
    private FacadeComunication facadec;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            facadeb = FacadeBack.getInstance();
            facadec = FacadeComunication.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }    

    @FXML
    private void confirmPassword(ActionEvent event) {
        if(tfPassword.getText().equals(facadeb.getSellerPassword())){
            try {
                int i = facadeb.sighRepassDocument();
                if(i > 0){
                    JSONObject reply = new JSONObject();
                    reply.accumulate("request", "sucessful repass");
                    reply.accumulate("rId", facadeb.getPassManager().getRealty());
                    facadec.sendMessage(reply.toString(), facadeb.getPassManager().getSellerhost(), facadeb.getPassManager().getSellerport());
                    
                    reply = new JSONObject();
                    reply.accumulate("Id", facadeb.getUser().getCpf());
                    reply.accumulate("request", "userAddRealty");
                    reply.accumulate("rId", i);
                    facadec.sendMessageToCourthouse(reply.toString());
                }
            } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | IOException | InvalidKeySpecException ex) {
                System.err.println(ex);
            }
        }
    }
    
}
