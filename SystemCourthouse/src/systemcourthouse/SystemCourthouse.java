package systemcourthouse;

import facade.FacadeComunication;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemCourthouse {

    public static void main(String[] args) {
        try {
            FacadeComunication facadec = FacadeComunication.getInstance();
            facadec.initializeCourtHouse(conect());
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }
    
    public static int conect(){
        System.out.println("Qual porta você quer conectar? ");
        Scanner reader = new Scanner(System.in);  
        return reader.nextInt();
    }
    
    public static void menu(){
        
    }
}
