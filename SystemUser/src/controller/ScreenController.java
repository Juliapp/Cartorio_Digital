package controller;


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
import util.Settings.Scenes;
import static util.Settings.Scenes.REALTY;

public class ScreenController {

    private final FacadeBack facadeb;
    private final Stage stage;
    private Realty actual;

    public ScreenController(Stage rootStage) throws IOException, ClassNotFoundException {
        stage = rootStage = new Stage();
        facadeb = FacadeBack.getInstance();
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
        int i = 0;
        List<Realty> ir = facadeb.getUserRealties();
        if(ir != null || ir.size() > 0){
            for (Realty realty : ir) {
                actual = realty;
                elements.add(createNewRealtyNode());
                i++;            
            }
        }    
        vbContainer.getChildren().setAll(elements);
    }
    
    private Node createNewRealtyNode(){
        Node node = null;
        try {
            node = (Node)FXMLLoader.load(getClass().getResource(REALTY.getValue()));
        } catch (IOException ex) {
            System.err.println("node nulo");
        }
       
        return node;
    }
    
    public Realty getActualRealty(){
        return actual;
    }
    
}    

