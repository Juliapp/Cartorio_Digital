package model;

import comunication.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *DAO da escritura
 * Manipulador de banco de dados
 * @author Juliana
 */
public class RealtyDAO {

    /**
     *Atualiza um registro no banco 
     * @param realties
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */    
    public Realty update(Realty realties) throws ClassNotFoundException, SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE Realty SET id = ?, hash = ?, address = ?,"
                    + " houseCharter = ?, signature = ?");
            System.out.println(stmt.toString());
            stmt.setInt(1, realties.getId());
            stmt.setString(2, realties.getHash());
            stmt.setString(3, realties.getAddress());
            stmt.setString(4, realties.getHouseCharter());
            stmt.setBytes(5, realties.getSignature());

            stmt.executeUpdate();
            System.out.println("Atualizado com sucesso!");
            return realties;
        } catch (SQLException ex) {
            System.out.println("Entrou aki no SQLException: " + ex.getMessage());
            System.out.println("Falha ao atualizar");
            return null;
        } finally {
            System.out.println("Finalizou a conexão");
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     *Pega um registro pelo seu ID
     * @param id
     * @return
     * @throws ClassNotFoundException
     */
    public Realty getRealtyById(Integer id) throws ClassNotFoundException {

        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Realty r = null;
            try {
                stmt = con.prepareStatement("SELECT * FROM Realty WHERE id = ?");
                stmt.setInt(1, id);
                rs = stmt.executeQuery();
                System.out.println("o id é: " + id);
                if (rs.next()) {
                    System.out.println("Buscando a escritura");
                    int getId = rs.getInt("id");
                    String hash = rs.getString("hash");
                    String address = rs.getString("address");
                    String houseCharter = rs.getString("houseCharter");
                    byte[] signature = rs.getBytes("signature");
                    r = new Realty(getId, hash, address, houseCharter, signature);
                } else {
                    //Lançar exeção
                    System.out.println("Não conseguiu entrar");
                    return null;
                }
                return r;
            } catch (SQLException ex) {
                System.err.println(ex);
            } finally {
                ConnectionFactory.closeConnection(con, stmt, rs);
            }
            return r;
        } catch (SQLException ex) {
            Logger.getLogger(RealtyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
