package view.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import facade.FacadeComunication;
import facade.FacadeFront;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.json.JSONObject;

public class FXMLLoginController implements Initializable {
    private FacadeFront facadef;
    private FacadeComunication facadec;
    
    @FXML   private JFXTextField txfEmail;
    @FXML   private JFXPasswordField txfPassword;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            facadec = FacadeComunication.getInstance();
            facadef = FacadeFront.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }

    }    

    @FXML
    private void checkLogin(ActionEvent event){ 
        JSONObject message = new JSONObject();
        message.accumulate("request", "validateUser");
        message.accumulate("email", txfEmail.getText());
        message.accumulate("password", txfPassword.getText());
        message.accumulate("host", facadec.getUserHost());
        message.accumulate("port", facadec.getUserPeerPort());
        facadec.sendMessageToCourthouse(message.toString());
        facadef.loadLoadingScreen();
    }

    @FXML
    private void logon(ActionEvent event) {
        facadef.loadLogonScreen();
    }
    
}
