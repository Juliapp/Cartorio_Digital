package view.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import facade.FacadeComunication;
import facade.FacadeFront;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.json.JSONObject;

public class FXMLLogonController implements Initializable {
    private FacadeComunication facadec;
    private FacadeFront facadef;

    
    @FXML   private JFXPasswordField tfPassword;
    @FXML   private JFXPasswordField tfConfirmPassword;
    @FXML   private JFXTextField tfName;
    @FXML   private JFXTextField tfEmail;
    @FXML   private JFXTextField tfCpf;
    @FXML   private Label warn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        warn.setVisible(false);
        try {
            facadec = FacadeComunication.getInstance();
            facadef = FacadeFront.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLLogonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }    

    @FXML
    private void logon(ActionEvent event) {
        if(!tfPassword.getText().equals(tfConfirmPassword.getText())){
            warn.setVisible(true);
        }else if(tfPassword.getText() != null && tfName.getText() != null && tfEmail.getText() != null && tfCpf.getText() !=  null){
            JSONObject message = new JSONObject();
            message.accumulate("request", "saveUser");
            message.accumulate("cpf", tfCpf.getText());
            message.accumulate("email", tfEmail.getText());
            message.accumulate("name", tfName.getText());
            message.accumulate("password", tfPassword.getText());
  
            facadec.sendMessageToCourthouse(message.toString());
            facadef.loadLoginScreen();
            
        }
    }

    @FXML
    private void backLogin(ActionEvent event) {
        facadef.loadLoginScreen();
    }
    
}
