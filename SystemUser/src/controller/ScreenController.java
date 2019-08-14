package controller;

import JPAPersistence.DAO;
import facade.FacadeBack;
import java.io.IOException;
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
import util.Settings.Scenes;
import static util.Settings.Scenes.REALTY;

public class ScreenController {

    private final FacadeBack facadeb;
    private final Stage stage;
    private Realty actual;
    private final DAO dao;

    public ScreenController(Stage rootStage) throws IOException, ClassNotFoundException {
        stage = rootStage = new Stage();
        facadeb = FacadeBack.getInstance();
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

    public void loadRealties(VBox vbContainer) {
        vbContainer.getChildren().clear();
        ArrayList<Node> elements = new ArrayList<>();
        try{
            List<Integer> list = facadeb.getUser().getRealties();
            if(list.size() > 0){
                for (Integer id : list) {
                    actual = dao.findRealty(((Integer) id));
                    elements.add(createNewRealtyNode());                       
                }
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
    
}    

