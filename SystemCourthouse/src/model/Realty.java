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
    private byte[] hash;
    private String address;
    @Column(length = 3000)
    private byte[] houseCharter;
    private byte[] signature;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getHouseCharter() {
        return houseCharter;
    }

    public void setHouseCharter(byte[] houseCharter) {
        this.houseCharter = houseCharter;
    }

    public byte[] getSignature() {
        return signature;
    }
    
    public void mergeNewSignature(byte[] signature, byte[] hash){
        this.signature = signature;
        this.hash = hash;
    }
    
    @Override
    public String toString() {
        JSONObject ts = new JSONObject();
        ts.accumulate("id", id);
        ts.accumulate("hash", hash);
        ts.accumulate("address", address);
        return ts.toString();
    }    
}