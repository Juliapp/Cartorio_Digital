package controller;

import facade.FacadeBack;
import facade.FacadeComunication;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import model.Realty;
import model.UserData;
import org.json.JSONObject;
import util.Cript;

/**
 *Controle de recebimento de mensagens
 * @author Juliana
 */
public class InputedDataProcess {

    private FacadeBack facadeb;
    private FacadeComunication facadec;
    private UserData userAux;
    private Realty realtyAux;
    private DaoController dao;
    private Cript cript;

    /**
     *
     */
    public InputedDataProcess() {
        try {
            facadeb = FacadeBack.getInstance();
            facadec = FacadeComunication.getInstance();
            userAux = new UserData();
            realtyAux = new Realty();
            dao = new DaoController();
            cript = new Cript();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }

    /**
     *Insere a mensagem, transforma em um JSON e a processa
     * @param inputedBytes
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void pullMessage(byte[] inputedBytes) throws ClassNotFoundException, SQLException {
        JSONObject message = new JSONObject(cript.UTF8encode(inputedBytes));
        System.out.println(message);
        JSONObject reply = null;
        switch (message.getString("request")) {
            case "conect":
                facadec.createNewPeerConection(message.getString("host"), message.getInt("port"));
                break;
            case "saveUser":
                userAux = new UserData();
                userAux.setCpf(message.getString("cpf"));
                userAux.setEmail(message.getString("email"));
                userAux.setName(message.getString("name"));
                userAux.setPassword(message.getString("password"));

                try {
                    message.getString("id");
                } catch (org.json.JSONException ex) {
                    try {
                        KeyPair pair = facadeb.DSAkeyPairGenerator();
                        userAux.setPrKey(facadeb.encodePrivateKey(pair.getPrivate()));
                        userAux.setPuKey(facadeb.encodePublicKey(pair.getPublic()));
                    } catch (NoSuchAlgorithmException ex1) {
                        System.err.println(ex1);

                    }
                }

                facadeb.saveUser(userAux);
                break;

            case "validateUser":
                reply = new JSONObject();
                userAux = dao.getUserByEmail(message.getString("email"));
                if (userAux != null) {
                    if (userAux.getPassword().equals(message.getString("password"))) {
                        reply.accumulate("reply", "sucessful login");
                        reply.accumulate("cpf", userAux.getCpf());
                        reply.accumulate("email", userAux.getEmail());
                        reply.accumulate("name", userAux.getName());
                        reply.accumulate("password", userAux.getPassword());
                        reply.accumulate("privateKey", userAux.getPrKey());
                        reply.accumulate("publicKey", userAux.getPuKey());
                        reply.accumulate("realties", userAux.getR());
                        facadec.sendMessage(reply.toString(), message.getString("host"), message.getInt("port"));
                    } else {
                        reply.accumulate("reply", "Erro");
                        reply.accumulate("message", "password incorrect");
                    }
                } else {
                    reply.accumulate("reply", "Erro");
                    reply.accumulate("message", "email is not registrated");
                    System.out.println(message.toString());
                    System.out.println(reply.toString());
                }

                facadec.sendMessage(reply.toString(), message.getString("host"), message.getInt("port"));
                break;
            case "signNewRealty":
                reply = new JSONObject();
                try {
                    Integer i = facadeb.signNewRealty(message.getJSONObject("realtyInfos"));
                    if (i != null) {
                        reply.accumulate("reply", "sucessful sign");
                        reply.accumulate("permitidId", i);
                        reply.accumulate("publicKey", facadeb.getCourt().getPuKey());
                        facadec.sendMessage(reply.toString(), message.getString("host"), message.getInt("port"));
                    } else {
                        reply.accumulate("reply", "Erro");
                        reply.accumulate("message", "something gone wrong on signature");
                    }

                } catch (InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException | SignatureException | IOException ex) {
                    System.err.println(ex);
                }

                break;
            case "addRealty":
                UserData user = facadeb.getUserById(message.getString("Id"));
                user.addRealty(message.getInt("rId"));
                if (facadeb.saveUser(user) != null) {
                    reply = new JSONObject();
                    reply.accumulate("reply", "sucessful save");
                    reply.accumulate("rId", message.getInt("rId"));
                    reply.accumulate("message", "Your data was susessful saved");
                    facadec.sendMessage(reply.toString(), message.getString("host"), message.getInt("port"));
                }
                break;

            case "userRemoveRealty":
                user = facadeb.getUserById(message.getString("Id"));
                user.removeRealty(message.getInt("rId"));

                facadeb.saveUser(user);
                break;

            case "userAddRealty":
                user = facadeb.getUserById(message.getString("Id"));
                user.addRealty(message.getInt("rId"));
                facadeb.saveUser(user);
                break;

            case "search":
                user = facadeb.getUserById(message.getString("id"));
                reply = new JSONObject();
                reply.accumulate("reply", "search");
                reply.accumulate("result", user.getR());
                facadec.sendMessage(reply.toString(), message.getString("host"), message.getInt("port"));
                break;
            default:
                break;
        }
    }
}
