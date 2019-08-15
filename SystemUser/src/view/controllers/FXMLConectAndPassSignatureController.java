package view.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import facade.FacadeBack;
import facade.FacadeComunication;
import facade.FacadeFront;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.json.JSONObject;

public class FXMLConectAndPassSignatureController implements Initializable {
    private FacadeFront facadef;
    private FacadeComunication facadec;
    private FacadeBack facadeb; 
    
    @FXML   private Pane passPassPane;
    @FXML   private Label passPassword;
    @FXML   private Pane conectPane;
    @FXML   private JFXTextField tfHost;
    @FXML   private JFXTextField tfPort;
    @FXML   private Pane passwordPane;
    @FXML   private JFXPasswordField passwordTF;
    @FXML   private JFXRadioButton tbLocalH;
    @FXML   private Label hostLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        passWPaneOn();
        try {
            facadef = FacadeFront.getInstance();
            facadec = FacadeComunication.getInstance();
            facadeb = FacadeBack.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }    

    @FXML
    private void conectToPeer(ActionEvent event) throws UnknownHostException {
        int port = Integer.parseInt(tfPort.getText());
        String host = InetAddress.getLocalHost().getHostAddress();
        if(tbLocalH.isSelected()){
            facadec.createNewPeerConection("localhost", port, askConectionToPeer(host, port));
            
        }
        else{
            facadec.createNewPeerConection(tfHost.getText(), Integer.parseInt(tfPort.getText()), askConectionToPeer(host, facadec.getUserPeerPort()));          
        }
        passPassword.setText(facadeb.createRandomCode());
        passPaneOn();
    }

    @FXML
    private void setLocalHostOn(ActionEvent event) {
        if(tbLocalH.isSelected()){
            tfHost.setText("127.0.0.1");
            tfHost.setDisable(true);
            hostLabel.setDisable(true);
        }
        else{
            tfHost.setText("");
            tfHost.setDisable(false);
            hostLabel.setDisable(false);
        }
    }

    @FXML
    private void confirmUserPassword(ActionEvent event) {
        System.out.println(passwordTF.getText());
        System.out.println(facadeb.getUser().getPassword());
        if(passwordTF.getText().equals(facadeb.getUser().getPassword())){
            conectPaneOn();
        }
    }
    
    public void passPaneOn(){
        passPassPane.setVisible(true);
        conectPane.setVisible(false);
        passwordPane.setVisible(false);
    }
    
    public void conectPaneOn(){
        passPassPane.setVisible(false);
        conectPane.setVisible(true);
        passwordPane.setVisible(false);
    }    

    public void passWPaneOn(){
        passPassPane.setVisible(false);
        conectPane.setVisible(false);
        passwordPane.setVisible(true);
    }

    public String askConectionToPeer(String host, int port){
        JSONObject message = new JSONObject();
        message.accumulate("request", "conect");
        message.accumulate("host", host);
        message.accumulate("port", facadec.getUserPeerPort());
        return message.toString();
    }    
}
