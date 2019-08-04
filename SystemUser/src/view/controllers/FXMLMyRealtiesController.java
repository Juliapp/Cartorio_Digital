package view.controllers;

import facade.FacadeBack;
import facade.FacadeFront;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class FXMLMyRealtiesController implements Initializable {
    private FacadeFront facadef;
    
    @FXML   private VBox vbContainer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            facadef = FacadeFront.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLMyRealtiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        facadef.loadRealties(vbContainer);
        
    }    
    
}
