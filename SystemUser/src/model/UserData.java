package model;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    private String cpf;
    private String name;
    private String email;
    private String password;
    private final ArrayList<Integer> realties;
    

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

    
}