package systemcourthouse;

import facade.FacadeBack;
import facade.FacadeComunication;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class SystemCourthouse {

    public static void main(String[] args) {
        try {
            //tenta ai só pra ver se cola
            FacadeComunication facadec = FacadeComunication.getInstance();
            facadec.initializeCourtHouse(conect());
       
            FacadeBack facadeb = FacadeBack.getInstance();
            facadeb.initialize();
        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException ex) {
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
