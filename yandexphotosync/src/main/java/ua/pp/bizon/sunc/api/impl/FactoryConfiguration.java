package ua.pp.bizon.sunc.api.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.pp.bizon.sunc.api.PathFactory;
import ua.pp.bizon.sunc.api.YandexUtils;
import ua.pp.bizon.sunc.remote.api.HttpUtil;
import ua.pp.bizon.sunc.remote.api.OAuth;
import ua.pp.bizon.sunc.remote.api.OAuthUI;
import ua.pp.bizon.sunc.remote.impl.HttpUtilImpl;
import ua.pp.bizon.sunc.remote.impl.OAuthCachedClient;
import ua.pp.bizon.sunc.remote.impl.OAuthImpl;
import ua.pp.bizon.sunc.remote.impl.YandexUtilsImpl;

@Configuration
public class FactoryConfiguration {
    @Bean
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
    public YandexUtils getYandexUtils() {
        return new YandexUtilsImpl();
    }
    
    @Bean OAuthUI getAuthUI() {
        return new OAuthCachedClient();
    }
}