package ua.pp.bizon.sunc.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpException;
import org.springframework.context.annotation.Bean;

import ua.pp.bizon.sunc.api.PathFactory;
import ua.pp.bizon.sunc.api.impl.PathFactoryImpl;
import ua.pp.bizon.sunc.remote.api.HttpUtil;
import ua.pp.bizon.sunc.remote.api.OAuth;
import ua.pp.bizon.sunc.remote.impl.RemoteException;
import ua.pp.bizon.sunc.remote.impl.ServiceDocument;
import ua.pp.bizon.sunc.remote.impl.ServiceEntry;
import ua.pp.bizon.sunc.remote.impl.ServiceEntryTestImpl;

@org.springframework.stereotype.Controller
public class Controller {

    @Bean public
    PathFactory getFactory() {
        return new PathFactoryImpl();
    }
    
    @Bean 
    public ServiceDocument getServiceDocument() {
        return new ServiceDocument();
    }
    
    @Bean public  ServiceEntry getServiceEntry() {
        return new ServiceEntryTestImpl();
    }
    
    @Bean public HttpUtil getHttpUtil() {
        return new HttpUtil() {
            
            @Override
            public void setAuth(OAuth auth) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public String postRawEntity(String url, String postData, String encode) throws IOException, RemoteException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public InputStream postEntry(String url, String data) throws IOException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public InputStream postData(String url, String name, byte[] data, String id) throws HttpException, IOException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public InputStream getStream(String url) throws HttpException, IOException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public OAuth getAuth() {
                // TODO Auto-generated method stub
                return null;
            }
        };
    }
    
    
}
