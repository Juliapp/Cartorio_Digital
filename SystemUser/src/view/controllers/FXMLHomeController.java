package view.controllers;

import com.jfoenix.controls.JFXButton;
import facade.FacadeFront;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import static util.Settings.Scenes.MY_REALTIES;
import static util.Settings.Scenes.NEW_REALTY;

public class FXMLHomeController implements Initializable {

    private FacadeFront facade;
    
    @FXML   private AnchorPane anchorMenu;
    @FXML   private JFXButton btnMyRealties;
    @FXML   private JFXButton btnRegRealty;
    @FXML   private JFXButton btnearch;

    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        try {
            facade = FacadeFront.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
        
    }    

    @FXML
    private void myRealties(ActionEvent event) {
        facade.setIsYours(true);
        facade.loadAnchorPane(anchorMenu, MY_REALTIES);
    }

    @FXML
    private void regRealty(ActionEvent event) {
        facade.loadAnchorPane(anchorMenu, NEW_REALTY);
    }


    @FXML
    private void search(ActionEvent event) {
        facade.setIsYours(false);
        facade.loadAnchorPane(anchorMenu, MY_REALTIES);
    }
    
}
