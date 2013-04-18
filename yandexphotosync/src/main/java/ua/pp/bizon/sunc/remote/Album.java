package ua.pp.bizon.sunc.remote;

import java.util.List;

import ua.pp.bizon.sunc.remote.impl.RemoteException;

public interface Album extends Collection, Entry {

    void createPhoto(String name, byte[] data) throws RemoteException;

    Album createAlbum(String name) throws RemoteException;

    boolean containsPhoto(String name);

    List<Entry> listPhotos();

}
