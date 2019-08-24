package model;

import comunication.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *DAO da escritura
 * Manipulador de banco de dados
 * @author Juliana
 */
public class RealtyDAO {

    /**
     *Cria um novo registro no banco 
     * @param realties
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Realty create(Realty realties) throws ClassNotFoundException, SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO `Realty`(`id`, `address`, `hash`, `houseCharter`, `signature`) VALUES (?,?,?,?,?)");
            int id = Math.abs(ThreadLocalRandom.current().nextInt());
            stmt.setInt(1, id);
            stmt.setString(2, realties.getAddress());
            stmt.setString(3, realties.getHash());
            stmt.setString(4, realties.getHouseCharter());
            stmt.setBytes(5, realties.getSignature());

            System.out.println(stmt.toString());
            stmt.executeUpdate();
            realties.setId(id);
            return realties;
        } catch (SQLException ex) {
            System.out.println("Entrou aki no SQLException: " + ex.getMessage());
            return null;
        } finally {
            System.out.println("Finalizou a conexão");
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

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
     *deleta um registro no banco 
     * @param r
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean delete(Realty r) throws ClassNotFoundException, SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM Realty WHERE id = ?");
            System.out.println(stmt.toString());
            stmt.setInt(1, r.getId());
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

                if (rs.next()) {
                    int getId = rs.getInt("id");
                    String hash = rs.getString("hash");
                    String address = rs.getString("address");
                    String houseCharter = rs.getString("houseCharter");
                    byte[] signature = rs.getBytes("signature");
                    r = new Realty(getId, hash, address, houseCharter, signature);
                } else {
                    //Lançar exeção
                    return null;
                }
                return r;
            } catch (SQLException ex) {
                ///Tratar!
            } finally {
                ConnectionFactory.closeConnection(con, stmt, rs);
            }
            return r;
        } catch (SQLException ex) {
            Logger.getLogger(RealtyDAO.class.getName()).log(Level.SEVERE, null, ex);
            ///Tratar!
        }
        return null;
    }

    /**
     *Pega todos os registros
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public List<Realty> getAllRealties() throws ClassNotFoundException, SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM Realty");
        ResultSet rs = stmt.executeQuery();
        LinkedList<Realty> realties = new LinkedList();

        while (rs.next()) {
            int getId = rs.getInt("id");
            String hash = rs.getString("hash");
            String address = rs.getString("address");
            String houseCharter = rs.getString("houseCharter");
            byte[] signature = rs.getBytes("signature");
            Realty realty = new Realty(getId, hash, address, houseCharter, signature);
            realties.add(realty);
        }
        ConnectionFactory.closeConnection(con, stmt, rs);
        return realties;
    }

}
