package ua.pp.bizon.sunc.remote;

import java.util.List;

import ua.pp.bizon.sunc.remote.impl.RemoteException;

public interface Collection extends Iterable<Entry>{
	
	Entry addEntry(Entry e);

	Entry findEntryByUrl(String url);
	
	Entry findEntryByName(String path);

	Entry createAlbum(String name) throws RemoteException;
    
    void addAll(List<Entry> entries);

    public abstract boolean isEmpty();

    List<Entry> listDirectories();
	
}
