package ua.pp.bizon.sunc.remote.impl;

import javax.swing.JOptionPane;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ua.pp.bizon.sunc.api.Store;
import ua.pp.bizon.sunc.remote.api.OAuth;
import ua.pp.bizon.sunc.remote.api.OAuthUI;

public class OAuthCachedClient implements OAuthUI {
    
    private static final String OAUTH_KEY = "oauth.key";
    private static final String CLIENT_ID = "oauth.client.id";
    private static final String CLIENT_SECRET = "oauth.client.secret";
    
    @Autowired
    private Store store;
    
    public OAuthCachedClient() {

    }
    
    public static void main(String args[]){
        OAuthCachedClient client =  new OAuthCachedClient();
        client.login();
    }

    @Override
    public void login(OAuth oAuthImpl) {
        if (store.get(OAUTH_KEY) == null) {
            login();
        }
        oAuthImpl.setToken(store.get(OAUTH_KEY));
    }

    protected void login() {
        String clientId = store.get(CLIENT_ID,"c69581cac83a4a19af04d5a03208d55f");
        String clientSecret = store.get(CLIENT_SECRET, "3d449b3adf4e437383acf61d1865c4c6");

        BrowserLoader.openUrl("https://oauth.yandex.ru/authorize?response_type=code&client_id=" + clientId);
        String code = JOptionPane.showInputDialog("input code here:");
        try {
            String postData = "grant_type=authorization_code&client_id=" + clientId+ "&code=" + code + "&client_secret=" + clientSecret;
            String jsonResponse = new HttpUtilImpl().postRawEntity("https://oauth.yandex.ru/token",postData,
                    "application/x-www-form-urlencoded").substring(18, 50);
            store.put(OAUTH_KEY, jsonResponse);
        }catch (Exception e){
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
    }

}
