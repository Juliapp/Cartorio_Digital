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

public class FXMLLoginController implements Initializable {
    private FacadeFront facadef;
    private FacadeComunication facadec;
    @FXML   private JFXTextField txfEmail;
    @FXML   private JFXPasswordField txfPassword;
    
    @FXML   private JFXTextField loginPort;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            facadec = FacadeComunication.getInstance();
            facadef = FacadeFront.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            
        }

    }    

    @FXML
    private void checkLogin(ActionEvent event){ 
        facadec.initializeUserPeer(Integer.parseInt(loginPort.getText()));
        facadef.loadConectScreen();
    }

    @FXML
    private void logon(ActionEvent event) {
        facadef.loadLogonScreen();
    }
    
}
