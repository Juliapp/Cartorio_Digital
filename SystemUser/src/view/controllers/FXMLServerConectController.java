package view.controllers;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import facade.FacadeComunication;
import facade.FacadeFront;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.json.JSONObject;

public class FXMLServerConectController implements Initializable {
    private FacadeFront facadef;
    private FacadeComunication facadec;
    
    @FXML   private JFXRadioButton tbLocalH;
    @FXML   private JFXTextField txfHost;
    @FXML   private JFXTextField txfPort;
    @FXML   private Label hostLabel;
    @FXML   private JFXTextField userPort;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            facadef = FacadeFront.getInstance();
            facadec = FacadeComunication.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }    

    @FXML
    private void isLocalhost(ActionEvent event) {
        if(tbLocalH.isSelected()){
            txfHost.setText("127.0.0.1");
            txfHost.setDisable(true);
            hostLabel.setDisable(true);
        }
        else{
            txfHost.setText("");
            txfHost.setDisable(false);
            hostLabel.setDisable(false);
        }
    }

    @FXML
    private void conectToCouthouHouse(ActionEvent event) {
        int serverPort = Integer.parseInt(txfPort.getText());
        int yourPort = Integer.parseInt(userPort.getText());
        
        if(tbLocalH.isSelected()){
            facadec.initializeUserPeer(yourPort);
            facadec.conectServer("localhost", serverPort, askConectionToPeer("localhost", serverPort));
        }
        else{
            facadec.conectServer(txfHost.getText(), serverPort, askConectionToPeer(facadec.getUserHost(), serverPort));          
        }
        facadef.loadLoginScreen();
    }
    
    public String askConectionToPeer(String host, int port){
        JSONObject message = new JSONObject();
        message.accumulate("request", "conect");
        message.accumulate("host", host);
        message.accumulate("port", facadec.getUserPeerPort());
        return message.toString();
    }
    
}
