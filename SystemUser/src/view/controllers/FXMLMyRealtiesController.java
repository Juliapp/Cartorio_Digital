package view.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import facade.FacadeFront;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class FXMLMyRealtiesController implements Initializable {
    private FacadeFront facadef;
    
    @FXML   private VBox vbContainer;
    
    @FXML   private JFXTextField txSearch;
    @FXML   private JFXButton btnSearch;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            facadef = FacadeFront.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
        
        if(facadef.isYour()){
            isYours();
            facadef.loadYourRealties(vbContainer);
        }else{
            isNotYours();
        }
       
    }    

    @FXML
    private void searchUser(ActionEvent event) {
        facadef.loadUserRealties(vbContainer, txSearch.getText());
    }
    
    
    public void isYours(){
        txSearch.setVisible(false);
        btnSearch.setVisible(false);
    }
    
    public void isNotYours(){
        txSearch.setVisible(true);
        btnSearch.setVisible(true);
    }    
}
