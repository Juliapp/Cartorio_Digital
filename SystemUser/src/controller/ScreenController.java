package controller;

import JPAPersistence.DAO;
import facade.FacadeBack;
import facade.FacadeComunication;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

public class ScreenController {

    private final FacadeBack facadeb;
    private final FacadeComunication facadec;
    private final Stage stage;
    private Realty actual;
    private final DAO dao;
    
    private boolean isYours;
    private String searchUser;

    public ScreenController(Stage rootStage) throws IOException, ClassNotFoundException {
        stage = rootStage = new Stage();
        facadeb = FacadeBack.getInstance();
        facadec = FacadeComunication.getInstance();
        dao = new DAO();
    }

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
    
    public void setAPChildren(AnchorPane anchor, Scenes screen){
        try {
            Node node = (Node)FXMLLoader.load(getClass().getResource(screen.getValue()));
            anchor.getChildren().setAll(node);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    public void setFullScreen(){
        stage.setFullScreen(true);
    }

    public void loadYourRealties(VBox vbContainer) {
        isYours = true;
        
        vbContainer.getChildren().clear();
        ArrayList<Node> elements = new ArrayList<>();
        try{
            List<Object> list = facadeb.getUser().getRealties();
            if(list.size() > 0){
                list.stream().map((id) -> {
                    actual = dao.findRealty((Integer)id);
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
                    actual = dao.findRealty((Integer)id);
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
    
    public Realty getActualRealty(){
        return actual;
    }
    
    public void newAlertError(String title, String mensege) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setContentText(mensege);
        a.show();
    }

    public void newAlertInformation(String title, String mensege) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setContentText(mensege);
        a.show();
    }
    
    public void newAlertWarn(String title, String mensege) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(title);
        a.setContentText(mensege);
        a.show();
    }    

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

