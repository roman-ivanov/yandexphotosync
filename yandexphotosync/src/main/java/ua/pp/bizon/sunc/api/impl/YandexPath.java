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

public class YandexPath implements Path {

    private Entry path;

    public YandexPath(Entry i) {
        this.path = i;
        if (i == null) {
            throw new NullPointerException();
        }
    }

    @Override
    public Path getParent() {
        return new YandexPath(path.getParent());
    }

    @Override
    public boolean isDirectory() {
        return (path instanceof Collection);
    }

    @Override
    public List<Path> listDirectories() throws RemoteException {
        LinkedList<Path> response = new LinkedList<Path>();
        if (path instanceof Collection) {
            for (Entry i : ((Collection) path).listDirectories()) {
                response.add(new YandexPath(i));
            }
        }
        return response;
    }

    @Override
    public List<Path> listFiles() throws RemoteException {
        LinkedList<Path> response = new LinkedList<Path>();
        if (path instanceof Album) {
            for (Entry i : ((Album) path).listPhotos()) {
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
    public void mkdir(String name) throws RemoteException {
        if (path instanceof Collection) {
            ((Collection) path).createAlbum(name);
        }
    }

    @Override
    public Path getChildren(String name) throws RemoteException {
        if (path instanceof Collection) {
            Entry child = ((Collection) path).findEntryByName(name);
            if (child != null)
                return new YandexPath(child);

        }
        return null;
    }

    @Override
    public byte[] getData() throws FileNotFoundException, IOException, RemoteException {
        if (path instanceof Photo) {
            return ((Photo) path).getData();
        } else
            throw new IOException("can't read data from album");
    }

    @Override
    public void uploadData(String name, byte[] data) throws IOException, RemoteException {
        if (path instanceof Album) {
            ((Album) path).createPhoto(name, data);
        } else
            throw new RemoteException("can't post photo data to photo");
    }

    @Override
    public boolean containsFile(String name) throws RemoteException {
        if (path instanceof Album) {
            return ((Album) path).containsPhoto(name);
        } else
            throw new RemoteException("can't post photo data to photo");
    }

    @Override
    public boolean containsFolder(String name) {
        if (path instanceof Collection) {
            return ((Collection) path).findEntryByName(name) != null;
        } else
            return false;
    }

    @Override
    public String toString() {
        return "yandex:" + path.getPath();
    }

    @Override
    public String getPath() {
        return toString();
    }

}
