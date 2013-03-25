package ua.pp.bizon.sunc.api;

import ua.pp.bizon.sunc.remote.Entry;
import ua.pp.bizon.sunc.remote.impl.RemoteException;

public interface YandexUtils {

    public abstract Entry loadEntry(String path) throws RemoteException;

}