package ua.pp.bizon.sunc.remote.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.pp.bizon.sunc.api.YandexUtils;
import ua.pp.bizon.sunc.remote.Album;
import ua.pp.bizon.sunc.remote.Entry;

public class YandexUtilsImpl implements YandexUtils {

    static Logger logger = LoggerFactory.getLogger(YandexUtilsImpl.class);

    public static Entry loadEntry(String path) throws RemoteException, IOException {
        ServiceDocument root = getRoot();
        return root.getAlbums().getOrCreatePath(path);
    }

    private static ServiceDocument root;

    private static ServiceDocument getRoot() {
        if (root == null) {
            root = new ServiceDocument();
        }
        return root;
    }

    public static Album createAlbum(String name) throws RemoteException {
        return root.createAlbum(name, null);
    }

    public static Album createAlbum(String name, String url) throws RemoteException {
        return root.createAlbum(name, url);
    }

    public static void createPhoto(String name, byte[] data, String id) throws RemoteException {
        root.createPhoto(name, data, id);
    }
}
