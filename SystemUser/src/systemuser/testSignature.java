package systemuser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ThreadLocalRandom;
import model.Realty;
import util.Cript;

public class testSignature {
    static String ALGORITHM = "DSA";
    static Cript cript = new Cript();
    
    static byte[] docSigh = null;
    
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, InvalidKeySpecException {
        
        //CONFIGURANDO OS DOIS USUÁRIOS      
        KeyPair keys = null;
        try {
            keys = DSAkeyPairGenerator();
        } catch (NoSuchAlgorithmException ex) {
            System.err.println(ex);
        }
        System.out.println("criou as chaves do vendedor");
        PrivateKey sellerPrK = keys.getPrivate();
        PublicKey sellerPuK = keys.getPublic();
        
        try {
            keys = DSAkeyPairGenerator();
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
            realty.setHouseCharter(cript.BASE64encode(Files.readAllBytes(file.toPath())));
        } catch (IOException ex) {
            System.err.println(ex);
        }
        
        realty = sighDocument(sellerPrK, realty);
        System.out.println(checkDocument(sellerPuK, realty));
        
        System.out.println(realty.getId());

        
    }
    
    public static KeyPair DSAkeyPairGenerator() throws NoSuchAlgorithmException{
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
        SecureRandom sr = new SecureRandom();
        kpg.initialize(512, sr);
        return kpg.generateKeyPair();
    }
    
    public static Realty sighDocument(PrivateKey privateKey, Realty realty) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, InvalidKeySpecException{
        //Initializing a new signature
        Signature signature = Signature.getInstance(ALGORITHM);
        signature.initSign(privateKey);
        //Gerate new Random Hash value and convert to String
        String newHash = Integer.toHexString(ThreadLocalRandom.current().nextInt());
        //Concat the house charter with the new hash
        String docNHashToSigh = signable(realty, newHash);
        System.out.println("Doc to sign " + docNHashToSigh);
        
        signature.update(cript.BASE64decode(docNHashToSigh));
        //gerate the new signature
        docSigh = signature.sign();
        
        System.out.println(docSigh);

        //update the new sign and the new hash
        realty.mergeNewSignature(docSigh, newHash);
        //update them in the database
        
//        realty = dao.mergeRealty(realty);
        
        //return the number of the realtyId to be added to the new owner
        return realty;
    }
    
    public static boolean checkDocument(PublicKey sellerPublicKey, Realty realty) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException{
        Signature sellerSig = Signature.getInstance(ALGORITHM);
        sellerSig.initVerify(sellerPublicKey);
        //the realty's hash is checked in the database, and garanties that is not an old hashcode
        sellerSig.update(cript.BASE64decode(signable(realty, realty.getHash())));

        return sellerSig.verify(docSigh);
    }
    
    public static String signable(Realty realty, String hash){
        return realty.getHouseCharter().substring(0, 50).concat(hash);
    }
}
