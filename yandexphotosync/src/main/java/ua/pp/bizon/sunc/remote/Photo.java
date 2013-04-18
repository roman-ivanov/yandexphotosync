package ua.pp.bizon.sunc.remote;

import ua.pp.bizon.sunc.remote.impl.RemoteException;

public interface Photo extends Entry {

	byte[] getData() throws RemoteException;

}
