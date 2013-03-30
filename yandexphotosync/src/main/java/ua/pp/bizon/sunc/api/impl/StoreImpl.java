package ua.pp.bizon.sunc.api.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.LoggerFactory;

import ua.pp.bizon.sunc.api.Store;

public class StoreImpl implements Store {

    private Properties properties = new Properties();
    private String path = "yandexphotosync.properties";

    public StoreImpl() {
        init();
    }

    public StoreImpl(String path) {
        this.path = path;
        init();
    }

    public void init() {
        try {
            properties.load(getClass().getResourceAsStream("/yandexphotosync.default.properties"));
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
    }

    @Override
    public String get(String key) {
        return get(key, null);
    }

    @Override
    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    @Override
    public String put(String key, String value) {
        Object ret = properties.put(key, value);
        try {
            properties.store(new FileOutputStream(path), "");
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
        return ret == null ? null: ret.toString();
    }

}
