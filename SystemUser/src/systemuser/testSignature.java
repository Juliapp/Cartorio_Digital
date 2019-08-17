package systemuser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import model.ManagerSecurity;
import model.Realty;
import util.Cript;

public class testSignature {
    
    
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, InvalidKeySpecException {
        ManagerSecurity m = new ManagerSecurity();
        Cript cript = new Cript();
        
        //CONFIGURANDO OS DOIS USUÁRIOS      
        KeyPair keys = null;
        try {
            keys = m.DSAkeyPairGenerator();
        } catch (NoSuchAlgorithmException ex) {
            System.err.println(ex);
        }
        System.out.println("criou as chaves do vendedor");
        PrivateKey sellerPrK = keys.getPrivate();
        PublicKey sellerPuK = keys.getPublic();
        
        try {
            keys = m.DSAkeyPairGenerator();
        } catch (NoSuchAlgorithmException ex) {
            System.err.println(ex);
        }

        System.out.println("criou as chaves do comprador");
        PrivateKey buyerPrK = keys.getPrivate();
        PublicKey buyerPuK = keys.getPublic();        
        
        
        //CONFIGURAR A CASA
        System.out.println("criou a casa");
        Realty realty = new Realty();
        System.out.println("setou o arquivo");
        realty.setId(4);
        realty.setAddress("testeDeAssinatura");   
        
        File file = new File("C:\\Users\\nana-\\OneDrive\\Documents\\UEFS\\4 semestre\\AmostrasDeEscritura\\Escritura1.txt");
        try {
            realty.setHouseCharter(Files.readAllBytes(file.toPath()));
        } catch (IOException ex) {
            System.err.println(ex);
        }
        
        System.out.println("assinatura");
        System.out.println(realty);
        realty = m.sighDocumentTESTE(sellerPrK, realty);
        
        System.out.println("checagem");
        if(sellerPuK == null){
            System.out.println("chave nula");
        }
        else{
            System.out.println("chave não nula");
        }
        System.out.println(m.checkDocument(sellerPuK, realty));
        
        System.out.println(realty.getId());
        
//        m.sighDocument(sellerPublicKey, privateKey, 0);
        
    }
    
}
