package view.controllers;

import facade.FacadeBack;
import facade.FacadeComunication;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class FXMLSearchController implements Initializable {
    
    private FacadeBack facadeb;
    private FacadeComunication facadec;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            facadeb = FacadeBack.getInstance();
            facadec = FacadeComunication.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }    
    
    //exibir busca
}
