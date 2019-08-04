package view.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import facade.FacadeFront;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class FXMLLoginController implements Initializable {
    private FacadeFront facade;
    @FXML   private JFXTextField txfEmail;
    @FXML   private JFXPasswordField txfPassword;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            facade = FacadeFront.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            
        }

    }    

    @FXML
    private void checkLogin(ActionEvent event){ 
        facade.loadHomeScreen();
    }

    @FXML
    private void logon(ActionEvent event) {
        facade.loadLogonScreen();
    }
    
}
