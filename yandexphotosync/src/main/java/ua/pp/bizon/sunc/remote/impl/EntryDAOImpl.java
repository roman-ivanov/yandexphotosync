package ua.pp.bizon.sunc.remote.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.pp.bizon.sunc.remote.Album;
import ua.pp.bizon.sunc.remote.Entry;
import ua.pp.bizon.sunc.remote.Photo;
import ua.pp.bizon.sunc.remote.api.EntryDAO;

public class EntryDAOImpl implements EntryDAO {
    
    private List<Entry>entries = new ArrayList<Entry>();
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private static int counter = 0;
    final int id;
    public EntryDAOImpl() {
        id = counter++;
        logger.trace("EntryDAOImpl "+ id);
    }

    @Override
    public Entry save(Entry e) {
        logger.trace("added entry("+ id +"): " + e);
         entries.add(e);
         return e;
    }

    @Override
    public Entry findEntryByUrl(String url) {
        Entry res = null;
        for (Entry e: entries){
            if (e.getUrl().equals(url)){
                res = e;
                break;
            }
            if (e instanceof Album) {
                res = ((Album) e).findEntryByUrl(url);
                if (res != null)
                    break;
            }
        }
        return res;
    }

    @Override
    public Entry findEntryByName(String name) {
        Entry res = null;
        for (Entry e: entries){
            if (e.getName().equals(name)){
                res = e;
                break;
            }
        }
        return res;
    }
    
    @Override
    public List<Entry> findEntriesByParentUrl(String url) {
        List<Entry> response = new ArrayList<>();
        for (Entry e: entries){
            if (e.getParentUrl() != null && e.getParentUrl().equals(url)){
                response.add(e);
            }
        }
        return response;
    }

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    @Override
    public List<Entry> getAllEntries() {
        return Collections.unmodifiableList(entries);
    }

    @Override
    public List<Entry> listDirectories() {
        List<Entry> dir = new ArrayList<>();
        for (Entry e: entries){
            if (e instanceof Album){
                dir.add(e);
            }
        }
        return dir;
    }

    @Override
    public List<Entry> listPhotos() {
        List<Entry> dir = new ArrayList<>();
        for (Entry e: entries){
            if (e instanceof Photo){
                dir.add(e);
            }
        }
        return dir;
    }
    
    
    @Override
    public void remove(Entry unsavedEntry) {
        entries.remove(unsavedEntry);
    }

}
