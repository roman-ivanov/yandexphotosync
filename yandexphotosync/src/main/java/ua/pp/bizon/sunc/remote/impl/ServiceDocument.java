package ua.pp.bizon.sunc.remote.impl;

import org.springframework.beans.factory.annotation.Autowired;

import ua.pp.bizon.sunc.remote.Album;
import ua.pp.bizon.sunc.remote.Photo;

public class ServiceDocument {

    @Autowired
    private ServiceEntry entry;

    protected ServiceEntry getServiceEntry() throws RemoteException {
        return entry;
    }
    
    public RootEntry getRoot() throws RemoteException {
        return getServiceEntry().getRootEntry();
    }
    
    void setEntry(ServiceEntry entry) {
        this.entry = entry;
    }

    public Album createAlbum(String name, String url) throws RemoteException {
        return getServiceEntry().createAlbum(name, url);
    }

    public Photo createPhoto(String name, byte[] data, String id) throws RemoteException {
        return getServiceEntry().createPhoto(name, data, id);
    }
}
