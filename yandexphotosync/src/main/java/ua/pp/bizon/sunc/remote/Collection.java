package ua.pp.bizon.sunc.remote;

import ua.pp.bizon.sunc.remote.impl.RemoteException;

public interface Collection extends Iterable<Entry>{
	
	Entry addEntry(Entry e);

	Entry findEntryByUrl(String url);
	
	Entry findEntryByName(String path);

	Entry createAlbum(String name) throws RemoteException;

    Entry getOrCreatePath(String path) throws RemoteException;

    void addAll(Collection entries);

    public abstract boolean isEmpty();
	
}
