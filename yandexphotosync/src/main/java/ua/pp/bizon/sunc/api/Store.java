package ua.pp.bizon.sunc.api;

public interface Store {

    String get(String key);
    String get(String key, String defaultValue);
    String put(String key, String value);
    
}
