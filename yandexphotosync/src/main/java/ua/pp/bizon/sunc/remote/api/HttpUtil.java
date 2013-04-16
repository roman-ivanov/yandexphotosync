package ua.pp.bizon.sunc.remote.api;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpException;

import ua.pp.bizon.sunc.remote.impl.RemoteException;


public interface HttpUtil {

    public abstract void setAuth(OAuth auth);

    public abstract OAuth getAuth();

    public abstract InputStream getStream(String url) throws HttpException, IOException;

    public abstract InputStream postEntry(String url, String data) throws IOException;

    public abstract InputStream postData(String url, String name, byte[] data, String id) throws HttpException,
            IOException;

    public abstract String postRawEntity(String url, String postData, String encode) throws IOException, RemoteException;

}