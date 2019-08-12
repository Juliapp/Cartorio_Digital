package JPAPersistence;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import model.Realty;
import model.UserData;

public class DAO {
    
    /*
        PERSISTENCE OF REALTIES
    */
    
    public Realty saveRealty(Realty realty){
        EntityManager em = new ConectionFactory().getConnection();
        
        try {
            em.getTransaction().begin();
            
            if(realty.getId() == null){
                em.persist(realty);
            }else{
                em.merge(realty);
            }
            em.getTransaction().commit();        
        } catch (Exception e) {
            System.err.println(e);
            em.getTransaction().rollback();
        }finally{
            em.close();
        }
        
        return realty;
    }
    
    public Realty findRealtyById(Integer id){
        EntityManager em = new ConectionFactory().getConnection();
        Realty realty = null;
        
        try {
            realty = em.find(Realty.class, id);
        } catch (Exception e) {
            System.err.println(e);
        }finally{
            em.close();
        }        
        
        return realty;
    }    
   
    public List<Realty> getAllRealties(){
        EntityManager em = new ConectionFactory().getConnection();
        List<Realty> realties = null;
        
        try{
            realties = em.createQuery("from Realty r").getResultList();
        } catch (Exception e) {
            System.err.println(e);
        }finally{
            em.close();
        }     
        
        return realties;
    }
    
    public Realty removeRealty(Integer id){
        EntityManager em = new ConectionFactory().getConnection();
        Realty realty = null;
        
        try{
            realty = findRealtyById(id);
            
            if(realty != null){
                em.getTransaction();
                em.remove(realty);
                em.getTransaction().commit();                
            }
            
        } catch (Exception e) {
            System.err.println(e);
            em.getTransaction().rollback();
        }finally{
            em.close();
        }            
        return realty;
    }
    
    /*
        PERSISTENCE OF USERS    
    */    
    
    public UserData saveUser(UserData user){
        EntityManager em = new ConectionFactory().getConnection();
        
        try {
            em.getTransaction().begin();
            
            if(user.getCpf() == null){
                em.persist(user);
            }else{
                em.merge(user);
            }
            em.getTransaction().commit();        
        } catch (Exception e) {
            System.err.println(e);
            em.getTransaction().rollback();
        }finally{
            em.close();
        }
        
        return user;
    }
    
    public UserData getUserById(String id){
        EntityManager em = new ConectionFactory().getConnection();
        UserData user = null;
        
        try {
            user = em.find(UserData.class, id);
        } catch (Exception e) {
            System.err.println(e);
        }finally{
            em.close();
        }        
        
        return user;
    }    
   
    public List<UserData> getAllUsers(){
        EntityManager em = new ConectionFactory().getConnection();
        List<UserData> user = null;
        
        try{
            user = em.createQuery("from UserData u").getResultList();
        } catch (Exception e) {
            System.err.println(e);
        }finally{
            em.close();
        }     
        
        return user;
    }
    
    public UserData removeUser(String id){
        EntityManager em = new ConectionFactory().getConnection();
        UserData user = null;
        
        try{
            user = getUserById(id);
            
            if(user != null){
                em.getTransaction();
                em.remove(user);
                em.getTransaction().commit();                
            }
            
        } catch (Exception e) {
            System.err.println(e);
            em.getTransaction().rollback();
        }finally{
            em.close();
        }            
        return user;
    }
    
    public List<Realty> getUserRealties(String id){
        List<Realty> realties = new ArrayList<>();
        
        UserData user = getUserById(id);
        if(user != null){
            List<Integer> ids = user.getReltiesIds();

            ids.forEach((id1) -> {
                realties.add(findRealtyById(id1));
            });
        }
        
        return realties;
    }
    
    public UserData getUserByEmail(String email){
        List<UserData> users = getAllUsers();
        for (UserData user : users) {
            if(user.getEmail().equals(email)){
                return user;
            }            
        }
        return null;
    }

    public void persistCourtHouse(PrivateKey prKey, PublicKey puKey) {
        if(getUserById("courthouse") == null){
            UserData court = new UserData();
            court.setCpf("courthouse");
            court.setPrKey(prKey);
            court.setPuKey(puKey);
            saveUser(court);
        }
    }

    public void persistCourtHouse(KeyPair keyPair) {
        if(getUserById("courthouse") == null){
            UserData court = new UserData();
            court.setCpf("courthouse");
            court.setPrKey(keyPair.getPrivate());
            court.setPuKey(keyPair.getPublic());
            saveUser(court);
        }        
    }
    
}

