package facade;

import controller.ScreenController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Realty;
import util.Settings;
import util.Settings.Scenes;

public class FacadeFront {
    
    private Stage rootStage;
    private ScreenController screenController;
    private static FacadeFront facade;
    
    private FacadeFront(){
    }

    public static synchronized FacadeFront getInstance() throws IOException, ClassNotFoundException {
        return (facade == null) ? facade = new FacadeFront(): facade;
    }  

    public void initializeRootStage(Stage stage) {
        this.rootStage = stage;
        
        try {
            screenController = new ScreenController(rootStage);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FacadeFront.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        screenController.loadRootScreen(Settings.Scenes.CONECT_SERVER);
    }
    
    public Stage getRootStage(){
        return rootStage;
    }
    
    public void loadLoginScreen(){
        screenController.loadRootScreen(Scenes.LOGIN);
    }

    public void loadHomeScreen() {
        screenController.loadRootScreen(Settings.Scenes.HOME);
    }
    
    public void loadConectScreen(){
        screenController.loadRootScreen(Settings.Scenes.CONECT_SERVER);
    }

    public void loadLogonScreen() {
        screenController.loadRootScreen(Settings.Scenes.LOGON);
    }
    
    public void loadLoadingScreen(){
        screenController.loadRootScreen(Settings.Scenes.LOADING);
    }
    
    public void loadAnchorPane(AnchorPane anchor, Scenes screen){
        screenController.setAPChildren(anchor, screen);
    }

    public void loadRealties(VBox vbContainer) {
        screenController.loadRealties(vbContainer);
    }
    
    public Realty getActualRealty(){
        return screenController.getActualRealty();
    }    
}
