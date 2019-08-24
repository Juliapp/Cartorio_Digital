package comunication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *Controlador de conexões. Classe para pegar a conexão e para fechá-la
 * @author Juliana
 */
public class ConnectionFactory {

    //Você deve preencher esse espaço com os dados do banco de dados 
    private static final String DRIVER;
    private static final String UNICODE;
    private static final String URL;
    private static final String USER;
    private static final String PASS;
    /**
     *Método para pegar a conexão
     * @return conexão
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        return DriverManager.getConnection(URL+UNICODE, USER, PASS); 
    }
    
    /**
     *Método para fechar a conexão
     * @param con
     * @throws SQLException
     */
    public static void closeConnection(Connection con) throws SQLException{
        if(con != null){
            con.close();
        }
    }
    
    /**
     *Método para fechar a conexão
     * @param con
     * @param stmt
     * @throws SQLException
     */
    public static void closeConnection(Connection con, PreparedStatement stmt) throws SQLException{
        closeConnection(con);
        if(stmt != null){
            stmt.close();
        }
    }
    
    /**
     *Método para fechar a conexão
     * @param con
     * @param stmt
     * @param r
     * @throws SQLException
     */
    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet r) throws SQLException{
        closeConnection(con, stmt);
        if(r != null){
            r.close();
        }
        
    }
}
