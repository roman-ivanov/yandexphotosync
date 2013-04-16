package ua.pp.bizon.sunc.remote.impl;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ua.pp.bizon.sunc.api.Store;
import ua.pp.bizon.sunc.remote.api.HttpUtil;
import ua.pp.bizon.sunc.remote.api.OAuth;
import ua.pp.bizon.sunc.remote.api.YandexLogin;

public class OAuthYandexClient implements YandexLogin {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected HttpUtil util;

    @Autowired
    protected Store store;

    @Override
    public String getOAuthKey(String login, String password) throws IOException {
        HttpClient client = new HttpClient();
        String clientId = // store.get(OAuth.CLIENT_ID,
        "b765f9edc02c4171a95b7aee7bcf2ef7";
        System.out.println(clientId);
        PostMethod method = new PostMethod("https://oauth.yandex.ru/token");
        String postData = "grant_type=password&client_id=" + clientId
                + "&client_secret=b17768c6d1484490bce7abfade3a04f6" + "&username=" + login + "&password=" + password;
        method.setRequestEntity(new StringRequestEntity(postData, "application/x-www-form-urlencoded", "UTF-8"));
        client.executeMethod(method);
        return method.getResponseBodyAsString();
    }

    @Override
    public String getOAuthKey(String token) throws IOException, RemoteException {
        logger.trace("getOAuthKey: token=" + token);
        String clientId = store.get(OAuth.CLIENT_ID);
        String clientSecret = store.get(OAuth.CLIENT_SECRET);
        String postData = "grant_type=authorization_code&client_id=" + clientId + "&code=" + token + "&client_secret="
                + clientSecret;
        String jsonResponse = util.postRawEntity("https://oauth.yandex.ru/token", postData,
                "application/x-www-form-urlencoded");
        if (jsonResponse.trim().charAt(0) == '{'){
            JSONObject obj = JSONObject.fromObject(jsonResponse);
            if (!obj.has("access_token")){
                throw new IOException("no token access_token here");
            }
            return obj.optString("access_token");
        } else 
            throw new RemoteException(jsonResponse);

    }

}
