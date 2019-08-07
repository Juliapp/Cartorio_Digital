package controller;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileController {
    
    public File fileMaker(){
        File file = new File("file");
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
}
