package model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

public class UserData {
    private String cpf;
    private String name;
    private String email;
    private String password;
    private String prKey;
    private String puKey;
    private List<Object> realties;
    
    public UserData(String cpf, String name, String email, String password) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.password = password;
        realties = new ArrayList<>();
    }
    
    public UserData() {
        realties = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setRealties(String realties){
        JSONArray array = new JSONArray(realties);
        if(array.length() > 0){
            for (Object id : array) {
                this.realties.add((Integer) id);
            }
        }
    }
    
    public void addRealty(Integer realtyId){
        this.realties.add(realtyId);
    }
    
    public void removeRealty(Integer realtyId){
        JSONArray array = new JSONArray(realties);
        for (int i = 0; i < array.length(); i++) {
            if(realtyId.equals(array.getInt(i))){
                array.remove(i);
                realties = array.toList();
                return;
            }            
        }
    }

    public List<Object> getRealties() {
        return realties;
    }

    public String getPrKey() {
        return prKey;
    }

    public void setPrKey(String prKey) {
        this.prKey = prKey;
    }

    public String getPuKey() {
        return puKey;
    }

    public void setPuKey(String puKey) {
        this.puKey = puKey;
    }
    
    public void setAllNull() {
        cpf = null;
        email = null;
        password = null;
        name = null;
    }    
    
}