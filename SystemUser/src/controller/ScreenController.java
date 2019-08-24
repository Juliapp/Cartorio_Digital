package controller;

import facade.FacadeBack;
import facade.FacadeComunication;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Realty;
import org.json.JSONArray;
import org.json.JSONObject;
import util.Settings.Scenes;
import static util.Settings.Scenes.PASSSTAGE;
import static util.Settings.Scenes.REALTY;
import static util.Settings.Scenes.RECIVESTAGE;

/**
 *Controlador de cenas (interface gráfica)
 * @author Juliana
 */
public class ScreenController {

    private final FacadeBack facadeb;
    private final FacadeComunication facadec;
    private final Stage stage;
    private Realty actual;
    private final DaoController dao;
    
    private boolean isYours;
    private String searchUser;


    public ScreenController(Stage rootStage) throws IOException, ClassNotFoundException {
        stage = rootStage = new Stage();
        facadeb = FacadeBack.getInstance();
        facadec = FacadeComunication.getInstance();
        dao = new DaoController();
    }

    /**
     *Faz o load para a cena principal
     * @param screen
     */
    public void loadRootScreen(Scenes screen){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(screen.getValue()));
        } catch (IOException ex) {
            System.err.println(ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();        
    }
    
    /**
     *
     * @param anchor
     * @param screen
     */
    public void setAPChildren(AnchorPane anchor, Scenes screen){
        try {
            Node node = (Node)FXMLLoader.load(getClass().getResource(screen.getValue()));
            anchor.getChildren().setAll(node);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    /**
     *Seta a cena como full screen
     */
    public void setFullScreen(){
        stage.setFullScreen(true);
    }

    /**
     *Faz o load das escrituras que o usuário possúi
     * @param vbContainer
     */
    public void loadYourRealties(VBox vbContainer) {
        isYours = true;
        
        vbContainer.getChildren().clear();
        ArrayList<Node> elements = new ArrayList<>();
        try{
            List<Object> list = facadeb.getUser().getRealties();
            if(list.size() > 0){
                list.stream().map((id) -> {
                    try {
                        actual = dao.findRealty((Integer)id);
                    } catch (ClassNotFoundException | SQLException ex) {
                        System.err.println(ex);
                    }
                    return id;
                }).forEachOrdered((_item) -> {
                    elements.add(createNewRealtyNode());
                });
            }      
        }catch(org.json.JSONException ex){
            
        }finally{
            vbContainer.getChildren().setAll(elements);
        }        
    }
    
    /**
     *Faz o load de pesquisa de outro usuário a partir do CPF (função não funcionando como deveria)
     * @param vbContainer
     * @param userid
     */
    public void loadOtherRealties(VBox vbContainer, String userid) {
        isYours = false;
        searchUser = null;
        
        JSONObject request = new JSONObject();
        request.accumulate("request", "search");
        request.accumulate("host", facadec.getUserHost());
        request.accumulate("port", facadec.getUserPeerPort());
        request.accumulate("id", userid);
        facadec.sendMessageToCourthouse(request.toString());
        
        while(searchUser == null){
            
        }
        
        JSONArray realties = new JSONArray(searchUser);
        
        vbContainer.getChildren().clear();
        ArrayList<Node> elements = new ArrayList<>();
        try{
            List<Object> list = realties.toList();
            if(list.size() > 0){
                list.stream().map((id) -> {
                    try {
                        actual = dao.findRealty((Integer)id);
                    } catch (ClassNotFoundException | SQLException ex) {
                        System.err.println(ex);
                    }
                    return id;
                }).forEachOrdered((_item) -> {
                    elements.add(createNewRealtyNode());
                });
            }      
        }catch(org.json.JSONException ex){
            
        }finally{
            vbContainer.getChildren().setAll(elements);
        }        
    }    
    
    private Node createNewRealtyNode(){
        Node node = null;
        try {
            node = (Node)FXMLLoader.load(getClass().getResource(REALTY.getValue()));
        } catch (IOException ex) {
            System.err.println("Realty node nulo");
        }
        return node;
    }
    
    /**
     *Pega a referência de escritura atual para ser mostrada na interface
     * @return
     */
    public Realty getActualRealty(){
        return actual;
    }
    

    /**
     *Abre uma nova cena para você setar as informações para envio de uma nova escritura
     */
    public void passScreen() {
        Stage passStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(PASSSTAGE.getValue()));
        } catch (IOException ex) {
            System.err.println(ex);
        }
        Scene scene = new Scene(root);
        passStage.setScene(scene);
        passStage.show();              
    }
   
    /**
     *Abre uma nova cena para receber uma nova escritura
     */
    public void reciveScreen() {
        Stage passStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(RECIVESTAGE.getValue()));
        } catch (IOException ex) {
            System.err.println(ex);
        }
        Scene scene = new Scene(root);
        passStage.setScene(scene);
        passStage.show();              
    }

    /**
     *Vê se a escritura de referência nessa classe é sua
     * @return
     */
    public boolean isIdYours() {
        return isYours;
    }

    public void setIdYours(boolean idYours) {
        this.isYours = idYours;
    }

    public String getSearchUser() {
        return searchUser;
    }

    public void setSearchUser(String searchUser) {
        this.searchUser = searchUser;
    }
    
}    

