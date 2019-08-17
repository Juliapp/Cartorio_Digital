package facade;

import controller.ScreenController;
import java.io.IOException;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Realty;
import util.Settings;
import util.Settings.Scenes;

public class FacadeFront {

    private ScreenController screenController;
    private static FacadeFront facade;

    private FacadeFront() {
    }

    public static synchronized FacadeFront getInstance() throws IOException, ClassNotFoundException {
        return (facade == null) ? facade = new FacadeFront() : facade;
    }

    public void initializeRootStage(Stage stage) {
        try {
            screenController = new ScreenController(stage);
            screenController.loadRootScreen(Settings.Scenes.CONECT_SERVER);
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }

    public void loadLoginScreen() {
        screenController.loadRootScreen(Scenes.LOGIN);
    }

    public void loadHomeScreen() {
        screenController.loadRootScreen(Settings.Scenes.HOME);
    }

    public void loadConectScreen() {
        screenController.loadRootScreen(Settings.Scenes.CONECT_SERVER);
    }

    public void loadLogonScreen() {
        screenController.loadRootScreen(Settings.Scenes.LOGON);
    }

    public void loadLoadingScreen() {
        screenController.loadRootScreen(Settings.Scenes.LOADING);
    }

    public void loadAnchorPane(AnchorPane anchor, Scenes screen) {
        screenController.setAPChildren(anchor, screen);
    }

    public void loadYourRealties(VBox vbContainer) {
        screenController.loadYourRealties(vbContainer);
    }
    
    public void loadUserRealties(VBox vbContainer, String userid){
            screenController.loadOtherRealties(vbContainer, userid);
    }
    
    public Realty getActualRealty() {
        return screenController.getActualRealty();
    }
    
    public void newAlertError(String title, String mensege) {
        screenController.newAlertError(title, mensege);
    }

    public void newAlertInformation(String title, String mensege) {
        screenController.newAlertInformation(title, mensege);
    }
    
    public void newAlertWarn(String title, String mensege) {
        screenController.newAlertWarn(title, mensege);
    }
    
    public void passScreen(){
        screenController.passScreen();
    }
    
    public void reciveScreen(){
        screenController.reciveScreen();
    }
    
    public boolean isYour(){
        return screenController.isIdYours();
    }

    public void setSearchResult(String searchUser) {
        screenController.setSearchUser(searchUser);
    }

    public void setIsYours(boolean b) {
        screenController.setIdYours(b);
    }
}
