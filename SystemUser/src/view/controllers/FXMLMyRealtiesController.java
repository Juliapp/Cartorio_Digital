package view.controllers;

import facade.FacadeFront;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
            System.err.println(ex);
        }
        
        facadef.loadRealties(vbContainer);
        
    }    
    
}
