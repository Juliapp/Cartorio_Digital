package facade;

import controller.ScreenController;
import java.io.IOException;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Realty;
import util.Settings;
import util.Settings.Scenes;

/**
 *
 * @author Juliana
 */
public class FacadeFront {

    private ScreenController screenController;
    private static FacadeFront facade;

    private FacadeFront() {
    }

    /**
     *Padrão singleton
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static synchronized FacadeFront getInstance() throws IOException, ClassNotFoundException {
        return (facade == null) ? facade = new FacadeFront() : facade;
    }

    /**
     *Inicializa a cena principal
     * @param stage
     */
    public void initializeRootStage(Stage stage) {
        try {
            screenController = new ScreenController(stage);
            screenController.loadRootScreen(Settings.Scenes.CONECT_SERVER);
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }

    /**
     *Carrega a cena de login
     */
    public void loadLoginScreen() {
        screenController.loadRootScreen(Scenes.LOGIN);
    }

    /**
     *Carrega a cena de home
     */
    public void loadHomeScreen() {
        screenController.loadRootScreen(Settings.Scenes.HOME);
    }

    /**
     *Carrega a cena de conexão com o cartório
     */
    public void loadConectScreen() {
        screenController.loadRootScreen(Settings.Scenes.CONECT_SERVER);
    }

    /**
     *Carrega a cena de cadastro
     */
    public void loadLogonScreen() {
        screenController.loadRootScreen(Settings.Scenes.LOGON);
    }

    /**
     *Carrega a cena de carregando (espera pela resposta do cartório)
     */
    public void loadLoadingScreen() {
        screenController.loadRootScreen(Settings.Scenes.LOADING);
    }

    /**
     *Carrega as páginas requisitadas pelo menu
     * @param anchor
     * @param screen
     */
    public void loadAnchorPane(AnchorPane anchor, Scenes screen) {
        screenController.setAPChildren(anchor, screen);
    }

    /**
     *Carrega suas escrituras 
     * @param vbContainer
     */
    public void loadYourRealties(VBox vbContainer) {
        screenController.loadYourRealties(vbContainer);
    }
    
    /**
     *Carrega a área de pesquisa das escrituras de outro usuário
     * @param vbContainer
     * @param userid
     */
    public void loadUserRealties(VBox vbContainer, String userid){
            screenController.loadOtherRealties(vbContainer, userid);
    }
    
    /**
     *Pega uma referência para setar o container de escrituras
     * @return
     */
    public Realty getActualRealty() {
        return screenController.getActualRealty();
    }
    
    /**
     *Abre a cena para passar uma escritura
     */
    public void passScreen(){
        screenController.passScreen();
    }
    
    /**
     *Abre a cena para receber uma escritura
     */
    public void reciveScreen(){
        screenController.reciveScreen();
    }
    
    /**
     *
     * @return
     */
    public boolean isYour(){
        return screenController.isIdYours();
    }

    /**
     *
     * @param searchUser
     */
    public void setSearchResult(String searchUser) {
        screenController.setSearchUser(searchUser);
    }

    /**
     *
     * @param b
     */
    public void setIsYours(boolean b) {
        screenController.setIdYours(b);
    }
}
