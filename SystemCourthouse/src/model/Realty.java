package model;

import org.json.JSONObject;

/**
 *Escritura
 * @author Juliana
 */
public class Realty {

    private Integer id;
    private String hash;
    private String address;
    private String houseCharter;
    private byte[] signature;

    /**
     *
     * @param id
     * @param hash
     * @param address
     * @param houseCharter
     * @param signature
     */
    public Realty(Integer id, String hash, String address, String houseCharter, byte[] signature) {
        this.id = id;
        this.hash = hash;
        this.address = address;
        this.houseCharter = houseCharter;
        this.signature = signature;
    }

    /**
     *
     */
    public Realty() {
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    } 
    
    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getHash() {
        return hash;
    }

    /**
     *
     * @param hash
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     */
    public String getHouseCharter() {
        return houseCharter;
    }

    /**
     *
     * @param houseCharter
     */
    public void setHouseCharter(String houseCharter) {
        this.houseCharter = houseCharter;
    }

    /**
     *
     * @return
     */
    public byte[] getSignature() {
        return signature;
    }
    
    /**
     *
     * @param signature
     * @param hash
     */
    public void mergeNewSignature(byte[] signature, String hash){
        this.signature = signature;
        this.hash = hash;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString() {
        JSONObject ts = new JSONObject();
        ts.accumulate("id", id);
        ts.accumulate("hash", hash);
        ts.accumulate("address", address);
        return ts.toString();
    }    
}