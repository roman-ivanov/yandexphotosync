package ua.pp.bizon.sunc.api.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import ua.pp.bizon.sunc.api.Store;

public class StoreImplTest {

    @Test
    public void testStoreImpl() {
        new StoreImpl();
    }

    @Test
    public void testStoreImplString() {
        new StoreImpl("yandexphotosync.test.properties");
    }
    
    @Test
    public void testStoreImplGetSet() {
        Store store = createTempStore();
        assertNull(store.get("test"));
        assertEquals("1", store.get("test", "1"));
        assertNull(store.put("test", "1"));
        assertEquals("1", store.get("test"));
        assertEquals("1", store.get("test", "2"));
    }

    public static Store createTempStore() {
        String filename = null;
        try {
             filename = File.createTempFile("yandexphotosync.temp.", ".properties", new File("/tmp")).getAbsolutePath();
        } catch (IOException e) {
            fail(e.getMessage());
        }
        Store store = new StoreImpl(filename);
        return store;
    }
    
}
