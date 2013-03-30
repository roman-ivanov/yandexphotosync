package ua.pp.bizon.sunc.remote.impl;

import org.springframework.beans.factory.annotation.Autowired;

import ua.pp.bizon.sunc.remote.Album;
import ua.pp.bizon.sunc.remote.Collection;
import ua.pp.bizon.sunc.remote.Photo;

public class ServiceDocument {

    private Collection albums = null;
    private Collection photos = null;

    public Collection getPhotos() throws RemoteException {
        if (photos == null)
            try {
                photos = getServiceEntry().getEntries();
            } catch (Exception e) {
                throw new RemoteException(e.getMessage(), e);
            }
        return photos;
    }

    public Collection getAlbums() throws RemoteException {
        if (albums == null)
            try {
                albums = getServiceEntry().getEntries();
            } catch (Exception e) {
                throw new RemoteException(e.getMessage(), e);
            }
        return albums;
    }

    @Autowired
    private ServiceEntry entry;

    private ServiceEntry getServiceEntry() throws RemoteException {
        return entry;
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

    public void init() {
        try {
            getAlbums();

            getPhotos();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
