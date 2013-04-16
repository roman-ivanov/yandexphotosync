package ua.pp.bizon.sunc.remote.impl;

import org.springframework.beans.factory.annotation.Autowired;

import ua.pp.bizon.sunc.remote.api.OAuth;
import ua.pp.bizon.sunc.remote.api.OAuthUI;

public class OAuthImpl implements OAuth {
    

    
    
    private String token;
    @Autowired
    private OAuthUI authUI;
    
    @Override
    public void setAuthUI(OAuthUI authUI) {
        this.token = null;
        this.authUI = authUI;
    }
    
    @Override
    public void setToken(String token) {
        this.token = token;
    }

	@Override
    public String getToken() {
	    if (token == null){
	        authUI.login(this);
	    }
        return token;
    }

}
