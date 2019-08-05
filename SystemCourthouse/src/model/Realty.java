package model;

public class Realty {
    private int hash;
    private String address;

    public Realty(int hash, String address) {
        this.hash = hash;
        this.address = address;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
}
