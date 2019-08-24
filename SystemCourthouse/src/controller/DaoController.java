package controller;

import java.sql.SQLException;
import java.util.List;
import model.Realty;
import model.RealtyDAO;
import model.UserData;
import model.UserDataDAO;

/**
 *Controlador do banco de dados
 * @author Juliana
 */
public class DaoController {

    //Banco de dados dos usuários
    private final UserDataDAO userDAO;
    //Banco de dados das escrituras
    private final RealtyDAO realtyDAO;


    public DaoController() {
        this.userDAO = new UserDataDAO();
        this.realtyDAO = new RealtyDAO();
    }

    /**
     *Salvar uma nova escritura ou fazer um merge dela
     * @param realty
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Realty saveRealty(Realty realty) throws ClassNotFoundException, SQLException {
        if (realty.getId() == null) {
            System.out.println("Escritura não existe e foi criada");
            return realtyDAO.create(realty);
        } else {
            System.out.println("Escritura foi atualizada");
            return realtyDAO.update(realty);
        } 
    }

    /**
     *Achar uma escritura a partir de seu ID
     * @param id
     * @return
     * @throws ClassNotFoundException
     */
    public Realty findRealtyById(Integer id) throws ClassNotFoundException {
        return realtyDAO.getRealtyById(id);
    }

    /**
     *Pegar a lista de escrituras
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public List<Realty> getAllRealties() throws ClassNotFoundException, SQLException {
        return realtyDAO.getAllRealties();
    }

    /**
     *Remover escirutra
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Realty removeRealty(Integer id) throws ClassNotFoundException, SQLException {
        Realty realty = findRealtyById(id);
        boolean sucess = false;
        if (realty.getId() != null) {
            System.out.println("Usuario deletado com sucesso" + id);
            sucess = realtyDAO.delete(realty);
        }        
        return sucess ? realty : null;
    };

    /*
        PERSISTENCE OF USERS    
     */

    /**
     *Dalvar ou fazer o merge de um usuário
     * @param user
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */

    public UserData saveUser(UserData user) throws ClassNotFoundException, SQLException {
        boolean sucess;
        System.out.println("Salvando usuario");
        if (getUserById(user.getCpf()) != null) {
            System.out.println("atualizando pois existe");
            sucess = userDAO.update(user);
        }else{
            System.out.println("Criando pois não exite");
            sucess = userDAO.create(user);
        }
        return sucess ? user : null;
    }

    /**
     *Pegar um usuário a partir de seu ID (CPF)
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public UserData getUserById(String id) throws ClassNotFoundException, SQLException {
        List <UserData> users = this.getAllUsers();
        System.out.println(users);
        System.out.println("pegnado usuario pelo id" + id);
        for (UserData user : users) {
            if(user.getCpf().equals(id)){
                System.out.println(user.getCpf());
                return user;
            }
        }   //Se ele não tá pegando quetentan dnvr dizer que está retornando null;
        return null;
    }

    /**
     *Pegar lista de usuários
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public List<UserData> getAllUsers() throws ClassNotFoundException, SQLException {
        return userDAO.getAllUsers();
    }

    /**
     *Remover usuário
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean removeUser(String id) throws ClassNotFoundException, SQLException {
        return userDAO.delete(id);
    }

    /**
     *Pegar lista de referências das escrituras de certo usuário com seu ID passando por parâmetro
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public List<Object> getUserRealties(String id) throws ClassNotFoundException, SQLException {
        UserData user = getUserById(id);
        return user.getRealties();
    }

    /**
     *Pegar usuário peglo email
     * @param email
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public UserData getUserByEmail(String email) throws ClassNotFoundException, SQLException {
        List<UserData> users = this.getAllUsers();

        for (UserData user : users) {
            if(user.getEmail().equalsIgnoreCase(email)){
                return user;
            }
        }
        return null;
    }

    /**
     *Pegar cartório
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public UserData persistCourtHouse() throws ClassNotFoundException, SQLException {
        System.out.println("Pegando o cartorio");
        return getUserById("courthouse");
    }

    /**
     *Persistir cartório (cria um cartório novo, com novas chaves)
     * @param encodedPrivateKey
     * @param encodedPublicKey
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public UserData persistCourtHouse(String encodedPrivateKey, String encodedPublicKey) throws ClassNotFoundException, SQLException {
        UserData user = new UserData();
        user.setCpf("courthouse");
        user.setPrKey(encodedPrivateKey);
        user.setPuKey(encodedPublicKey);
        return saveUser(user);
    }

}
