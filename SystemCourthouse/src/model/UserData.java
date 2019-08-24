package model;

import java.util.List;
import org.json.JSONArray;

/**
 *Informações do usuário
 * @author Juliana
 */
public class UserData {
    private String cpf;
    private String name;
    private String email;
    private String password;
    private String prKey;
    private String puKey;
    private String realties;
    
    /**
     *
     * @param cpf
     * @param name
     * @param email
     * @param password
     */
    public UserData(String cpf, String name, String email, String password) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.password = password;
        realties = new JSONArray().toString();
    }    
    
    /**
     *
     */
    public UserData() {
        realties = new JSONArray().toString();
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getCpf() {
        return cpf;
    }

    /**
     *
     * @param cpf
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     *
     * @param realtyId
     */
    public void addRealty(Integer realtyId){
        JSONArray array = new JSONArray(realties);
        array.put(realtyId);
        realties = array.toString();
    }
    
    /**
     *
     * @param realtyId
     */
    public void removeRealty(Integer realtyId){
        JSONArray array = new JSONArray(realties);
        for (int i = 0; i < array.length(); i++) {
            if(realtyId.equals(array.getInt(i))){
                array.remove(i);
                realties = array.toString();
                return;
            }            
        }
    }
   
    /**
     *
     * @return
     */
    public List<Object> getRealties() {
        return new JSONArray(realties).toList();
    }
    
    /**
     *
     * @return
     */
    public String getR(){
       return realties;
   }
   
    /**
     *
     * @param r
     */
    public void setR(String r){
        realties = r;
    }

    /**
     *
     * @return
     */
    public String getPrKey() {
        return prKey;
    }

    /**
     *
     * @param prKey
     */
    public void setPrKey(String prKey) {
        this.prKey = prKey;
    }

    /**
     *
     * @return
     */
    public String getPuKey() {
        return puKey;
    }

    /**
     *
     * @param puKey
     */
    public void setPuKey(String puKey) {
        this.puKey = puKey;
    }
    

    
}