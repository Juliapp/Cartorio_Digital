package systemuser;

import facade.FacadeComunication;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SystemUser extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
//        
//        Scene scene = new Scene(root);
//        
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        launch(args);
        FacadeComunication facadeComunication = FacadeComunication.getInstance();
        facadeComunication.createNewPeerConection("127.0.0.1", 5555);
        
    }
    
}
