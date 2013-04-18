package ua.pp.bizon.sunc.remote.impl;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.pp.bizon.sunc.remote.Album;
import ua.pp.bizon.sunc.remote.Collection;
import ua.pp.bizon.sunc.remote.Entry;
import ua.pp.bizon.sunc.remote.api.EntryDAO;

public class RootEntry implements Entry, Collection {

    public RootEntry(ServiceEntry entry) {
        this.serviceEntry = entry;
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

    EntryDAO dao = new EntryDAOImpl();
    private ServiceEntry serviceEntry;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Entry addEntry(Entry e) {
        if (e instanceof Album) {
            for (Entry child : dao.findEntriesByParentUrl(e.getUrl())) {
                dao.remove(child);
                ((Album) e).addEntry(child);
            }
        }
        if (e.getParentUrl() == null)
            return dao.save(e);
        else {
            if (dao.findEntryByUrl(e.getParentUrl()) != null) {
                return ((Album) dao.findEntryByUrl(e.getParentUrl())).addEntry(e);
            } else {
                logger.warn(e.toString());
                dao.save(e);
                return e;
            }
        }
    }

    @Override
    public Entry findEntryByUrl(String url) {
        return dao.findEntryByUrl(url);
    }

    @Override
    public Entry findEntryByName(String path) {
        return dao.findEntryByName(path);
    }

    @Override
    public void addAll(List<Entry> entries) {
        for (Entry e : entries) {
            addEntry(e);
        }
    }

    @Override
    public boolean isEmpty() {
        return dao.isEmpty();
    }

    @Override
    public Iterator<Entry> iterator() {
        return dao.getAllEntries().iterator();
    }

    @Override
    public Entry createAlbum(String name) throws RemoteException {
        Album newEntry = serviceEntry.createAlbum(name, null);
        return addEntry(newEntry);
    }

    @Override
    public List<Entry> listDirectories() {
        return dao.listDirectories();
    }

}
