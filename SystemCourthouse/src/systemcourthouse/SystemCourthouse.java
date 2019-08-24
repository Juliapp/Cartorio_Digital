package systemcourthouse;

import facade.FacadeBack;
import facade.FacadeComunication;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Juliana
 */
public class SystemCourthouse {

    /**
     *inicializa o cartório 
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        try {
            FacadeComunication facadec = FacadeComunication.getInstance();
            facadec.initializeCourtHouse(conect());
       
            FacadeBack facadeb = FacadeBack.getInstance();
            facadeb.initialize();
        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException ex) {
            System.err.println(ex);
        }
    }
    
    /**
     *Primeira conexão, deixa a porta disponível
     * @return
     */
    public static int conect(){
        System.out.println("Qual porta você quer conectar? ");
        Scanner reader = new Scanner(System.in);  
        return reader.nextInt();
    }
    
}
