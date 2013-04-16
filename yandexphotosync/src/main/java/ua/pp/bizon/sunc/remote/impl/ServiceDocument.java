package ua.pp.bizon.sunc.remote.impl;

import org.springframework.beans.factory.annotation.Autowired;

import ua.pp.bizon.sunc.remote.Album;
import ua.pp.bizon.sunc.remote.Collection;
import ua.pp.bizon.sunc.remote.Photo;

public class ServiceDocument {

    @Autowired
    private ServiceEntry entry;

    private ServiceEntry getServiceEntry() throws RemoteException {
        return entry;
    }
    
    public Collection getEntries() throws RemoteException {
        return getServiceEntry().getEntries();
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
