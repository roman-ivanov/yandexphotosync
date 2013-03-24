package ua.pp.bizon.sunc.api.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import ua.pp.bizon.sunc.api.Path;
import ua.pp.bizon.sunc.remote.Album;
import ua.pp.bizon.sunc.remote.Collection;
import ua.pp.bizon.sunc.remote.Entry;
import ua.pp.bizon.sunc.remote.Photo;
import ua.pp.bizon.sunc.remote.impl.RemoteException;
import ua.pp.bizon.sunc.remote.impl.YandexUtilsImpl;

public class YandexPath implements Path {

	private Entry path;

	public YandexPath(String path) throws PathNotSupportedException {
		try {
			this.path = YandexUtilsImpl.loadEntry(path);
		} catch (Exception e) {
			throw new PathNotSupportedException(e.getMessage(), e);
		}
	}

	public YandexPath(Entry i) {
		this.path = i;
	}

	@Override
	public boolean isDirectory() {
		return (path instanceof Collection);
	}

	@Override
	public List<Path> listFiles() throws RemoteException {
		LinkedList<Path> response = new LinkedList<Path>();
		if (path instanceof Collection) {
			for (Entry i : (Collection) path) {
				response.add(new YandexPath(i));
			}
		}
		return response;
	}

	@Override
	public String getName() {
		return path.getName();
	}

	@Override
	public Path mkDirAndCD(String name) throws RemoteException {
		if (path instanceof Collection) {
			return new YandexPath(((Collection) path).createAlbum(name));
		} else
			throw new RemoteException("path is not a directory: " + path);
	}

	@Override
	public byte[] getData() throws FileNotFoundException, IOException {
		if (path instanceof Photo) {
			return ((Photo) path).getData();
		} else
			throw new IOException("can't read data from album");
	}

	@Override
	public void uploadData(String name, byte[] data) throws IOException,
			RemoteException {
		if (path instanceof Album) {
			((Album)path).createPhoto(name, data);
		} else
			throw new RemoteException("can't post photo data to photo");
	}
	
	@Override
	public boolean containsFile(String name) throws RemoteException {
	    if (path instanceof Album) {
            return ((Album)path).containsPhoto(name);
        } else
            throw new RemoteException("can't post photo data to photo");
    	}

}
