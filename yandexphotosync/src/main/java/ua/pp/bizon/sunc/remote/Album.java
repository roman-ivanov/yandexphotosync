package ua.pp.bizon.sunc.remote;

import ua.pp.bizon.sunc.remote.impl.RemoteException;

public interface Album extends Collection, Entry {

    void createPhoto(String name, byte[] data) throws RemoteException;

    Entry createAlbum(String name) throws RemoteException;

    boolean containsPhoto(String name);

}
