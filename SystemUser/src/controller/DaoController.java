package controller;

import java.sql.SQLException;
import model.Realty;
import model.RealtyDAO;

/**
 *Controlador do banco de dados
 * @author Juliana
 */
public class DaoController {
    //Banco de dados das escrituras
    private final RealtyDAO realtyDAO;

    public DaoController() {
        this.realtyDAO = new RealtyDAO();
    }

    /**
     *Fazer um merge da escritura
     * @param realty
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Realty mergeRealty(Realty realty) throws ClassNotFoundException, SQLException {
        if (realty.getId() != null) {
             return realtyDAO.update(realty);
        }
        return null;
    }
    
    /**
     *Buscar o hash atual da escritura a partir de seuID
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public String findRealtyHash(Integer id) throws ClassNotFoundException, SQLException {
        return this.findRealty(id).getHash();
    }
    
    /**
     *Buscar escritura a partir de seu ID
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Realty findRealty(Integer id) throws ClassNotFoundException, SQLException {
        return realtyDAO.getRealtyById(id);
    }

}
