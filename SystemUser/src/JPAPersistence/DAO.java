package JPAPersistence;

import JPAPersistence.ConectionFactory;
import javax.persistence.EntityManager;
import model.Realty;

public class DAO {
    
    /*
        PERSISTENCE OF REALTIES
    */
    
    public Realty mergeRealty(Realty realty){
        EntityManager em = new ConectionFactory().getConnection();
        
        try {
            em.getTransaction().begin();
            
            if(realty.getId() != null){
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
    
    public byte[] findRealtyHash(Integer id){
        EntityManager em = new ConectionFactory().getConnection();
        try {
            return em.find(Realty.class, id).getHash();
        } catch (Exception e) {
            System.err.println(e);
        }finally{
            em.close();
        }        
        
        return null;
    }
    
    public Realty findRealty(Integer id){
        EntityManager em = new ConectionFactory().getConnection();
        try {
            return em.find(Realty.class, id);
        } catch (Exception e) {
            System.err.println(e);
        }finally{
            em.close();
        }        
        
        return null;
    } 
    
}

