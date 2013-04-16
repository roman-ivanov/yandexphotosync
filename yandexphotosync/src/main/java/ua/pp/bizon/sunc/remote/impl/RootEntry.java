package ua.pp.bizon.sunc.remote.impl;

import java.util.Iterator;

import ua.pp.bizon.sunc.remote.Collection;
import ua.pp.bizon.sunc.remote.Entry;

public class RootEntry implements Entry, Collection {
    
    Collection collectionImpl;

    public RootEntry(Collection collectionImpl) {
        this.collectionImpl = collectionImpl;
    }

    @Override
    public String getParentUrl() {
        return null;
    }

    @Override
    public void setParent(Entry entry) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPath() {
        return "/";
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Entry getParent() {
        return null;
    }

    @Override
    public Entry addEntry(Entry e) {
        return collectionImpl.addEntry(e);
    }

    @Override
    public Entry findEntryByUrl(String url) {
        return collectionImpl.findEntryByUrl(url);
    }

    @Override
    public Entry findEntryByName(String path) {
        return collectionImpl.findEntryByName(path);
    }

    @Override
    public Entry getOrCreatePath(String path) throws RemoteException {
        return collectionImpl.getOrCreatePath(path);
    }

    @Override
    public void addAll(Collection entries) {
        collectionImpl.addAll(entries);
    }

    @Override
    public boolean isEmpty() {
        return collectionImpl.isEmpty();
    }

    @Override
    public Iterator<Entry> iterator() {
        return collectionImpl.iterator();
    }


    @Override
    public Entry createAlbum(String name) throws RemoteException {
        return collectionImpl.createAlbum(name);
    }

    public void setEnclosingEntry(Entry enclosingEntry) {
        throw new UnsupportedOperationException();
    }

    public Entry getEnclosingEntry() {
        throw new UnsupportedOperationException();
    }

}
