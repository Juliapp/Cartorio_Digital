package facade;

import controller.DaoController;
import controller.SecurityManagerController;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;
import model.Realty;
import model.UserData;
import org.json.JSONObject;

/**
 *
 * @author Juliana
 */
public class FacadeBack {

    private static FacadeBack facade;
    private UserData courthouse;
    private final DaoController daoController;
    private final SecurityManagerController managerSecurity;

    private FacadeBack(){
        daoController = new DaoController();
        managerSecurity = new SecurityManagerController();
    }

    /**
     *Padrão singleton
     * @return
     */
    public static synchronized FacadeBack getInstance() {
        return (facade == null) ? facade = new FacadeBack() : facade;
    }

    /**
     *Inicializa o cartório
     * Para inicializar um cartório novo, ou se quiser refatorar o cartório existente no banco de dados
     * tire o comentário dentro deste método. Não se esqueça de pôr o comentário de volta
     * @throws NoSuchAlgorithmException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void initialize() throws NoSuchAlgorithmException, ClassNotFoundException, SQLException {
//        KeyPair pair = DSAkeyPairGenerator();
        courthouse = daoController.persistCourtHouse(/*encodePrivateKey(pair.getPrivate()),
        encodePublicKey(pair.getPublic())*/);
    }

    public UserData getCourt() {
        return courthouse;
    }

    /**
     *
     * @param realty
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Realty saveRealty(Realty realty) throws ClassNotFoundException, SQLException {
        return daoController.saveRealty(realty);
    }

    /**
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     */
    public Realty findRealtyById(Integer id) throws ClassNotFoundException {
        return daoController.findRealtyById(id);
    }

    /**
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public List<Realty> getAllRealties() throws ClassNotFoundException, SQLException {
        return daoController.getAllRealties();
    }

    /**
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Realty removeRealty(Integer id) throws ClassNotFoundException, SQLException {
        return daoController.removeRealty(id);
    }

    /*
        PERSISTENCE OF USERS    
     */

    /**
     *
     * @param user
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */

    public UserData saveUser(UserData user) throws ClassNotFoundException, SQLException {
        return daoController.saveUser(user);
    }

    /**
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public UserData getUserById(String id) throws ClassNotFoundException, SQLException {
        return daoController.getUserById(id);
    }

    /**
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public List<UserData> getAllUsers() throws ClassNotFoundException, SQLException {
        return daoController.getAllUsers();
    }

    /**
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean removeUser(String id) throws ClassNotFoundException, SQLException {
        return daoController.removeUser(id);
    }

    /**
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public List<Object> getUserRealties(String id) throws ClassNotFoundException, SQLException {
        return daoController.getUserRealties(id);
    }

    /**
     *
     * @param email
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public UserData getUserByEmail(String email) throws ClassNotFoundException, SQLException {
        return daoController.getUserByEmail(email);
    }

    /**
     *Assina um novo documento
     * @param jsonObject
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Integer signNewRealty(JSONObject jsonObject) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, ClassNotFoundException, SQLException {
        System.out.println(courthouse.getPrKey());
        Realty r = managerSecurity.signDocument(courthouse.getPrKey(), jsonObject);
        r = daoController.saveRealty(r);
        return r != null ? r.getId() : 0;
    }

    /**
     *
     * @param publicKey
     * @return
     */
    public String encodePublicKey(PublicKey publicKey) {
        return managerSecurity.encodePublicKey(publicKey);
    }

    /**
     *
     * @param privateKey
     * @return
     */
    public String encodePrivateKey(PrivateKey privateKey) {
        return managerSecurity.encodePrivateKey(privateKey);
    }

    /**
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public KeyPair DSAkeyPairGenerator() throws NoSuchAlgorithmException, NoSuchAlgorithmException {
        return managerSecurity.DSAkeyPairGenerator();
    }

}
