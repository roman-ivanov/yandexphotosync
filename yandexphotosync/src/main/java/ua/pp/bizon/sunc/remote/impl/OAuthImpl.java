package ua.pp.bizon.sunc.remote.impl;

import ua.pp.bizon.sunc.App;
import ua.pp.bizon.sunc.remote.api.OAuth;
import ua.pp.bizon.sunc.remote.api.OAuthUI;

public class OAuthImpl implements OAuth {
    
    
    private String token;
    
    @Override
    public void setToken(String token) {
        this.token = token;
    }

	@Override
    public String getToken() {
	    if (token == null){
	        App.context.getBean(OAuthUI.class).login(this);
	    }
        return token;
    }

}
