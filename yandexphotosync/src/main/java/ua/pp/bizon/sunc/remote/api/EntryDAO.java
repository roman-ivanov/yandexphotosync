package ua.pp.bizon.sunc.remote.api;

import java.util.List;

import ua.pp.bizon.sunc.remote.Entry;

public interface EntryDAO {

    Entry save(Entry e);

    Entry findEntryByUrl(String url);
    
    Entry findEntryByName(String name);

    boolean isEmpty();

    List<Entry> getAllEntries();

    List<Entry> listDirectories();
    
    List<Entry> listPhotos();

    void remove(Entry unsavedEntry);

    List<Entry> findEntriesByParentUrl(String url);
    
}
