package model;

import java.util.ArrayList;
import java.util.Iterator;

public class UserData {
    private String name;
    private String email;
    private String cpf;
    private String password;
    private final ArrayList<Realty> realties;
    
    
    
//    private EletronicSignature eSignature;
    //Usar o singlleton 

    public UserData(String name) {
        this.name = name;
        realties = new ArrayList<>();
    }
    
    
    
    
//    private class EletronicSignature {
//        //aqui vai ficar a assinatura 
//    }
    
//    private class CertifyAlthority {
//        private String name;
//        private String hash;
//        private EletronicSignature eSignature;
//    }
//    

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
    
    public Iterator<Realty> getIterRelties(){
        return realties.iterator();
    }
    
    public void addRealty(Realty realty){
        realties.add(realty);
    }
    
    public void removeRealty(Realty realty){
        realties.remove(realty);
    }
    
//    public void getRealty(){
//        realties.
//    }
            
}