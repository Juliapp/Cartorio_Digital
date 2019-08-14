package model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.json.JSONArray;

@Entity
public class UserData {
    @Id
    private String cpf;
    private String name;
    private String email;
    private String password;
    @Column(length = 1000)
    private String prKey;
    @Column(length = 1000)
    private String puKey;
    private String realties;
    
    public UserData(String cpf, String name, String email, String password) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.password = password;
        realties = new JSONArray().toString();
    }
    
    public UserData() {
        realties = new JSONArray().toString();
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
    
    public void addRealty(Integer realtyId){
        JSONArray j = new JSONArray(realties);
        System.out.println(j);
        j.put(realtyId);
        realties = j.toString();
    }
    
    public void removeRealty(Integer realtyId){
        JSONArray array = new JSONArray(realties);
        for (int i = 0; i < array.length(); i++) {
            if(((Integer)array.get(i)).equals(realtyId)){
                array.remove(i);
                return;
            }            
        }
    }
   
    public List<Object> getRealties() {
        return new JSONArray(realties).toList();
    }
    
   public String getR(){
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