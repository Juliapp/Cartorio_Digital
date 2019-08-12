package model;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserData {
    @Id
    private String cpf;
    private String name;
    private String email;
    private String password;
    private PrivateKey prKey;
    private PublicKey puKey;
    private final ArrayList<Integer> realties;
    
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
    
    public List<Integer> getReltiesIds(){
        return realties;
    }
    
    public void addRealty(Integer realtyId){
        realties.add(realtyId);
    }
    
    public void removeRealty(Integer realtyId){
        realties.remove(realtyId);
    }

    public List<Realty> getRealties() {
        return (List)realties;
    }

    public PrivateKey getPrKey() {
        return prKey;
    }

    public void setPrKey(PrivateKey prKey) {
        this.prKey = prKey;
    }

    public PublicKey getPuKey() {
        return puKey;
    }

    public void setPuKey(PublicKey puKey) {
        this.puKey = puKey;
    }
    
    public void setAllNull() {
        cpf = null;
        email = null;
        password = null;
        name = null;
    }    
    
}