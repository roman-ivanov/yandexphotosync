package ua.pp.bizon.sunc.remote.impl;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ua.pp.bizon.sunc.api.Store;
import ua.pp.bizon.sunc.remote.api.HttpUtil;
import ua.pp.bizon.sunc.remote.api.OAuth;
import ua.pp.bizon.sunc.remote.api.OAuthUI;
import ua.pp.bizon.sunc.remote.api.YandexLogin;

public class OAuthCachedClient implements OAuthUI {
    
    private static final String OAUTH_KEY = "oauth.key";
    @Autowired
    private Store store;
    @Autowired
    private HttpUtil util;
    @Autowired
    private YandexLogin login;
    
    public OAuthCachedClient() {
        //LoggerFactory.getLogger(getClass()).trace("started unused class:", new Exception());
    }

    @Override
    public void login(OAuth oAuthImpl) {
        if (store.get(OAUTH_KEY) == null) {
            try {
                login();
            } catch (IOException | RemoteException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }
        }
        oAuthImpl.setToken(store.get(OAUTH_KEY));
    }

    protected void login() throws IOException, RemoteException {
        String clientId = store.get(OAuth.CLIENT_ID);
        BrowserLoader.openUrl("https://oauth.yandex.ru/authorize?response_type=code&client_id=" + clientId);
        String code = JOptionPane.showInputDialog("input code here:");
        String key = login.getOAuthKey(code);
        store.put(OAUTH_KEY, key);
    }

}
