package ua.pp.bizon.sunc.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.pp.bizon.sunc.api.PathFactory;
import ua.pp.bizon.sunc.api.Store;
import ua.pp.bizon.sunc.remote.api.HttpUtil;
import ua.pp.bizon.sunc.remote.api.OAuth;
import ua.pp.bizon.sunc.remote.api.OAuthUI;
import ua.pp.bizon.sunc.remote.api.YandexLogin;
import ua.pp.bizon.sunc.remote.impl.HttpUtilImpl;
import ua.pp.bizon.sunc.remote.impl.OAuthCachedClient;
import ua.pp.bizon.sunc.remote.impl.OAuthImpl;
import ua.pp.bizon.sunc.remote.impl.OAuthYandexClient;
import ua.pp.bizon.sunc.remote.impl.RemoteException;
import ua.pp.bizon.sunc.remote.impl.ServiceDocument;
import ua.pp.bizon.sunc.remote.impl.ServiceEntry;

@Configuration
public class FactoryConfiguration {

    private static final String START_URL = "app.start.url";
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Bean(name="pathFactory")
    public PathFactory getFactory() {
        return new PathFactoryImpl();
    }

    @Bean
    public OAuth getOAuth() {
        return new OAuthImpl();
    }

    @Bean
    public HttpUtil getHttpUtil() {
        return new HttpUtilImpl();
    }

    @Bean
    public
    OAuthUI getAuthUI() {
        return new OAuthCachedClient();
    }

    @Bean
    public ServiceEntry getServiceEntry() throws RemoteException {
        ServiceEntry entry = null;
        try {
            entry = new ServiceEntry(applicationContext.getBean(Store.class).get(START_URL));
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
        return entry;
    }

    @Bean
    public ServiceDocument getServiceDocument() throws RemoteException {
        return new ServiceDocument();
    }

    @Bean
    public Store getStore() {
        return new StoreImpl();
    }
    
    @Bean
    public YandexLogin getYandexLogin() {
        return new OAuthYandexClient();
    }
}