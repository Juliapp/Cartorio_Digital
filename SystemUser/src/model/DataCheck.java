package model;

import java.util.Observable;

/**
 *Observable para a mudança da tela de carregando as informações
 * @author Juliana
 */
public class DataCheck extends Observable{
    public boolean sucessfullLogin;

    public boolean isSucessfullLogin(){
        return sucessfullLogin;
    }

    public void setSucessfullLogin(boolean sucessfullLogin) {
        this.sucessfullLogin = sucessfullLogin;
        this.notifyScreen();
    }

    private void notifyScreen() {
        setChanged();
        notifyObservers(sucessfullLogin);
    }
    
    
}
