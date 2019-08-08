package controller;

import java.io.File;
import java.io.IOException;

public class FileController {
    
    public File fileMaker(){
        File file = new File("file");
        try {
            file.createNewFile();
        } catch (IOException ex) {
            System.err.println(ex);
        }
        
        return null;
        
    }
    
}
