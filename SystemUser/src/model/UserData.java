package model;

public class UserData {
    private String name;
    private String email;
    private String password;
    private EletronicSignature eSignature;
    //Usar o singlleton 
    
    
    
    
    private class EletronicSignature {
        //aqui vai ficar a assinatura 
    }
    
    private class CertifyAlthority {
        private String name;
        private String hash;
        private EletronicSignature eSignature;
    }
    
}