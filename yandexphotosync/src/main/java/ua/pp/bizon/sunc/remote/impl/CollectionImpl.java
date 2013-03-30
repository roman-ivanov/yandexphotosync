package ua.pp.bizon.sunc.remote.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.pp.bizon.sunc.remote.Album;
import ua.pp.bizon.sunc.remote.Collection;
import ua.pp.bizon.sunc.remote.Entry;

public class  CollectionImpl implements Collection {

    private Map<String, Entry> mapByUrl = new HashMap<String, Entry>();
    private Map<String, Entry> mapByName = new HashMap<String, Entry>();
    private Logger logger = LoggerFactory.getLogger(getClass());
    protected List<Entry> unsortedMapByUrl = new LinkedList<Entry>();
    private Album element;
    private final ServiceDocument root;
    
    public CollectionImpl(ServiceDocument root) {
        this.root = root;
    }


    @Override
    public Entry addEntry(Entry e) {
        logger.trace("add entry: " + e.getUrl() + ", parent" + e.getParentUrl() + " to " + this);
        if (e.getParentUrl() != null) {
            Entry parentEntry = mapByUrl.get(e.getParentUrl());
            if (parentEntry != null) {
                ((Collection) parentEntry).addEntry(e);
                e.setParent(parentEntry);
            } else {
                unsortedMapByUrl.add(e);
            }
        }
        for (Iterator<Entry> i = unsortedMapByUrl.iterator(); i.hasNext();) {
            Entry ie = i.next();
            if (ie.getParentUrl().equals(e.getUrl())) {
                ie.setParent(e);
                if (e instanceof Collection) {
                    ((Collection) e).addEntry(ie);
                }
                i.remove();
            }
        }
        mapByUrl.put(e.getUrl(), e);
        mapByName.put(e.getName(), e);
        return e;
    }

    @Override
    public Entry findEntryByUrl(String url) {
        return mapByUrl.get(url);
    }

    @Override
    public Entry findEntryByName(String path) {
        return mapByName.get(path);
    }
    
    @Override
    public void addAll(Collection entries) {
        for (Entry i: entries){
            addEntry(i);
        }
    }

    @Override
    public Iterator<Entry> iterator() {
        return new IteratorImpl();
    }

    private class IteratorImpl implements Iterator<Entry> {

        private Iterator<String> iterator = mapByName.keySet().iterator();

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Entry next() {
            String key = iterator.next();
            return mapByName.get(key);
        }

        @Override
        public void remove() {
            throw new RuntimeException("can't be removed");
        }
    }

    @Override
    public Entry getOrCreatePath(String path) throws RemoteException {
        if (path.startsWith("/")){
            path = path.substring(1);
        }
        String splittedPath[] = path.split("/", 2);
        String name = splittedPath[0];
        String subPath = splittedPath.length > 1 ? splittedPath[1] : null;
        logger.debug("getOrCreatePath: path = " + path + ", name = " + name + ", subpath =" + subPath);
        if (!mapByName.containsKey(name)) {
            createAlbum(name);
        }
        if (mapByName.get(name) instanceof Collection && subPath != null) {
            return ((Collection) mapByName.get(name)).getOrCreatePath(subPath);
        } else {
            return mapByName.get(name);
        }
    }

    @Override
    public Entry createAlbum(String name) throws RemoteException {
       if (element != null){
           return addEntry(element.createAlbum(name));
       }else {
           return addEntry(root.createAlbum(name, null));
       }
    }

    @Override
    public String toString() {
        Iterator<Entry> i = iterator();
        if (!i.hasNext())
            return "CollectionImpl:{}";

        StringBuilder sb = new StringBuilder();
        sb.append("CollectionImpl:{");
        for (;;) {
            Entry e = i.next();
            sb.append(e);
            sb.append("\n");
            if (!i.hasNext())
                return sb.append('}').toString();
            sb.append(", ");
        }
    }

    public Album getElement() {
        return element;
    }

    public void setElement(Album element) {
        this.element = element;
    }

}
