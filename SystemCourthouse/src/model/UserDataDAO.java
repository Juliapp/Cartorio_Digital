package model;

import comunication.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Juliana
 */
public class UserDataDAO {

    /**
     *Cria um novo registro no banco 
     * @param user
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean create(UserData user) throws ClassNotFoundException, SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO UserData "
                    + "(cpf, name, email, password, prKey, puKey, realties)"
                    + "VALUES(?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, user.getCpf());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getPrKey());
            stmt.setString(6, user.getPuKey());
            stmt.setString(7, user.getR());
            System.out.println("-===================UUSER: "+ user);
            System.out.println(stmt.toString());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Entrou aki no SQLException: " + ex.getMessage());
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     *Atualiza um registro no banco 
     * @param user
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean update(UserData user) throws ClassNotFoundException, SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE UserData SET cpf = ?, name = ?,"
                    + " email = ?, password = ?, prKey = ?, puKey = ?, realties = ?");

            stmt.setString(1, user.getCpf());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getPrKey());
            stmt.setString(6, user.getPuKey());
            stmt.setString(7, user.getR());
            System.out.println("Atualizando usuario");
            System.out.println(stmt.toString());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Entrou aki no SQLException: " + ex.getMessage());
            return false;
        } finally {
            System.out.println("Finalizou a conexão");
            ConnectionFactory.closeConnection(con, stmt);
        }
    }


    /**
     *deleta um registro no banco 
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean delete(String id) throws ClassNotFoundException, SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM UserData WHERE cpf = ?");
            System.out.println(stmt.toString());
            stmt.setString(1, id);
            stmt.executeUpdate();
            System.out.println("Deletado com sucesso");
            return true;
        } catch (SQLException ex) {
            System.out.println("Entrou aki no SQLException: " + ex.getMessage());
            return false;
        } finally {
            System.out.println("Finalizou a conexão");
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    /**
     *Pega todos os registros
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public LinkedList<UserData> getAllUsers() throws ClassNotFoundException, SQLException {
        Connection con = ConnectionFactory.getConnection(); //ve esse aquê que eu não entendi vvv
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM UserData");
        ResultSet rs = stmt.executeQuery();
        LinkedList alunos = new LinkedList();
        System.out.println("Pegando todos os alunos list---");
        while (rs.next()) {
            String cpf = rs.getString("cpf");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String prKey = rs.getString("prKey");
            String puKey = rs.getString("puKey");
            String realties = rs.getString("realties");

            UserData user = new UserData(cpf, name, email, password);
            user.setPrKey(prKey);
            user.setPuKey(puKey);
            user.setR(realties);
            System.out.println(user);
            alunos.add(user);
        } 
        ConnectionFactory.closeConnection(con, stmt, rs);
        System.out.println("conexão fechada -- Get allUsers");
        return alunos;
    } 
 
       
}
