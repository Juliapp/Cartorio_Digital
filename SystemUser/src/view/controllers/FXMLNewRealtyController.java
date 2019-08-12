package view.controllers;

import com.jfoenix.controls.JFXTextField;
import facade.FacadeComunication;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.json.JSONObject;

public class FXMLNewRealtyController implements Initializable {

    @FXML   private JFXTextField tfAddress;
    @FXML   private Label charterPath;
    
    private FileChooser fileChooser;
    private File selectedFile;
    private FacadeComunication facadec;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        charterPath.setVisible(false);
        
        try {
            facadec = FacadeComunication.getInstance();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }    

    @FXML
    private void search(ActionEvent event) {
        fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            charterPath.setVisible(true);
            charterPath.setText(selectedFile.getName());
        }
      
    }

    @FXML
    private void registerNewRealty(ActionEvent event) {
        if(selectedFile.isFile() && !tfAddress.getText().equals("")){
            JSONObject message = new JSONObject();
            message.accumulate("request", "newRealty");
            message.accumulate("host", facadec.getUserHost());
            message.accumulate("port", facadec.getUserPeerPort());
            message.accumulate("charter", pathToByteString(selectedFile));
            facadec.sendMessageToCourthouse(message.toString());  
        }
    }
    
    private String pathToByteString(File file){
        try {
            return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return null;
    }
    
}
