package facade;

import JPAPersistence.DAO;
import controller.SecurityManagerController;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import model.Realty;
import model.UserData;
import org.json.JSONObject;

public class FacadeBack {

    private static FacadeBack facade;
    private UserData courthouse;
    private final DAO dao;
    private final SecurityManagerController managerSecurity;

    private FacadeBack(){
        dao = new DAO();
        managerSecurity = new SecurityManagerController();
    }

    public static synchronized FacadeBack getInstance() {
        return (facade == null) ? facade = new FacadeBack() : facade;
    }

    public void initialize() throws NoSuchAlgorithmException {
//        KeyPair pair = DSAkeyPairGenerator();
        courthouse = dao.persistCourtHouse(/*encodePrivateKey(pair.getPrivate()),
encodePublicKey(pair.getPublic())*/);
    }

    public UserData getCourt() {
        return courthouse;
    }

    public Realty saveRealty(Realty realty) {
        return dao.saveRealty(realty);
    }

    public Realty findRealtyById(Integer id) {
        return dao.findRealtyById(id);
    }

    public List<Realty> getAllRealties() {
        return dao.getAllRealties();
    }

    public Realty removeRealty(Integer id) {
        return dao.removeRealty(id);
    }

    /*
        PERSISTENCE OF USERS    
     */
    public UserData saveUser(UserData user) {
        return dao.saveUser(user);
    }

    public UserData getUserById(String id) {
        return dao.getUserById(id);
    }

    public List<UserData> getAllUsers() {
        return dao.getAllUsers();
    }

    public UserData removeUser(String id) {
        return dao.removeUser(id);
    }

    public List<Object> getUserRealties(String id) {
        return dao.getUserRealties(id);
    }

    public UserData getUserByEmail(String email) {
        return dao.getUserByEmail(email);
    }

    public Integer signNewRealty(JSONObject jsonObject) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        return managerSecurity.signDocument(courthouse.getPrKey(), jsonObject);
    }

    public String encodePublicKey(PublicKey publicKey) {
        return managerSecurity.encodePublicKey(publicKey);
    }

    public String encodePrivateKey(PrivateKey privateKey) {
        return managerSecurity.encodePrivateKey(privateKey);
    }

    public KeyPair DSAkeyPairGenerator() throws NoSuchAlgorithmException, NoSuchAlgorithmException {
        return managerSecurity.DSAkeyPairGenerator();
    }

}
