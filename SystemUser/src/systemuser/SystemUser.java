package systemuser;

import facade.FacadeFront;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

public class SystemUser extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FacadeFront facadef = FacadeFront.getInstance();
        facadef.initializeRootStage(stage);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        launch(args);
        
    }
    
}
