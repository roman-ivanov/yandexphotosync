package ua.pp.bizon.sunc.remote.api;

import java.io.IOException;

import ua.pp.bizon.sunc.remote.impl.RemoteException;

public interface YandexLogin {
    
    String getOAuthKey(String login, String password) throws IOException;
    
    String getOAuthKey(String token) throws IOException, RemoteException;

}
