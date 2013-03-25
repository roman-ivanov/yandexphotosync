package ua.pp.bizon.sunc.remote.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.slf4j.LoggerFactory;

import ua.pp.bizon.sunc.remote.api.OAuth;
import ua.pp.bizon.sunc.remote.api.OAuthUI;

public class OAuthCachedClient implements OAuthUI {
    
    private static final String OAUTH_KEY = "OAUTH_KEY";

    private static String PATH = "yandexphotosync.oauth.properties";
    
    private Properties properties;
    
    public OAuthCachedClient() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(PATH));
        } catch (IOException e) {
            // ignored
        }
    }
    
    public static void main(String args[]){
        OAuthCachedClient client =  new OAuthCachedClient();
        client.login();
        System.out.println(client.properties);
    }

    @Override
    public void login(OAuth oAuthImpl) {
        if (properties.containsKey(OAUTH_KEY)) {
            oAuthImpl.setToken(properties.getProperty(OAUTH_KEY));
        } else { 
            login();
            oAuthImpl.setToken(properties.getProperty(OAUTH_KEY));
            try {
                properties.store(new FileOutputStream(PATH), "");
            } catch (IOException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }
        }

    }

    protected void login() {
        String clientId = properties.getProperty("CLIENT_ID");
        String clientSecret = properties.getProperty("CLIENT_SECRET");
        BrowserLoader.openUrl("https://oauth.yandex.ru/authorize?response_type=code&client_id=" + clientId);
        String code = JOptionPane.showInputDialog("input code here:");
        try {
            String postData = "grant_type=authorization_code&client_id=" + clientId+ "&code=" + code + "&client_secret=" + clientSecret;
            String jsonResponse = new HttpUtilImpl().postRawEntity("https://oauth.yandex.ru/token",postData,
                    "application/x-www-form-urlencoded").substring(18, 50);
            properties.setProperty(OAUTH_KEY, jsonResponse);
        }catch (Exception e){
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
    }

}
