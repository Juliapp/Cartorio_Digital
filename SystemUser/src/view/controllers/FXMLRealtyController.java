package view.controllers;

import facade.FacadeBack;
import facade.FacadeFront;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Realty;

public class FXMLRealtyController implements Initializable {
    
    private Realty realty;
    private FacadeFront facade;
    private FacadeBack facadeb;
    
    @FXML   private Label something;
    @FXML   private Label address;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            facade = FacadeFront.getInstance();
            facadeb = FacadeBack.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
        realty = facade.getActualRealty();
        address.setText(realty.getAddress());
    }    

    public void setSomething(Label something) {
        this.something = something;
    }


    @FXML
    private void passSignature(ActionEvent event) {
        facadeb.setRealtyToPass(realty.getId());
        facade.passScreen();
    }
    
    
}
