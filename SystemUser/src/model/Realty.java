package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.json.JSONObject;

@Entity
public class Realty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    private Integer id;
    private String hash;
    private String address;
    @Column(length = 3000)
    private String houseCharter;
    private byte[] signature;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHouseCharter() {
        return houseCharter;
    }

    public void setHouseCharter(String houseCharter) {
        this.houseCharter = houseCharter;
    }

    public byte[] getSignature() {
        return signature;
    }
    
    public void mergeNewSignature(byte[] signature, String hash){
        this.signature = signature;
        this.hash = hash;
    }
    
    @Override
    public String toString() {
        JSONObject ts = new JSONObject();
        ts.accumulate("hash", hash);
        ts.accumulate("address", address);
        return ts.toString();
    }    
}