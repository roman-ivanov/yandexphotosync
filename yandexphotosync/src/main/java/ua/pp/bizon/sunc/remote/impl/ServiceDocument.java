package ua.pp.bizon.sunc.remote.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import ua.pp.bizon.sunc.remote.Album;
import ua.pp.bizon.sunc.remote.Collection;
import ua.pp.bizon.sunc.remote.Photo;

public class ServiceDocument {

	public Collection getPhotos() throws RemoteException {
		try {
			return getServiceEntry().loadPhotos();
		} catch (Exception e) {
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public Collection getAlbums() throws RemoteException {
		try {
			return getServiceEntry().loadAlbums();
		} catch (Exception e) {
			throw new RemoteException(e.getMessage(), e);
		}

	}

	private ServiceEntry entry;

	private ServiceEntry getServiceEntry() throws RemoteException {
		if (entry == null) {
			try {
				entry = new ServiceEntry("http://api-fotki.yandex.ru/api/me/");
			} catch (Exception e) {
				throw new RemoteException(e.getMessage(), e);
			}
		}
		return entry;
	}

    public Album createAlbum(String name, String url) throws RemoteException {  
        return getServiceEntry().createAlbum(name, url);
    }

    public Photo createPhoto(String name, byte[] data, String id) throws RemoteException {
        return getServiceEntry().createPhoto(name, data, id);
    }
}
