package ua.pp.bizon.sunc.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import ua.pp.bizon.sunc.remote.impl.RemoteException;

public interface Path {

    boolean isDirectory();

    List<Path> listDirectories() throws RemoteException;

    String getName();

    Path mkDirAndCD(String name) throws RemoteException;

    byte[] getData() throws FileNotFoundException, IOException;

    void uploadData(String name, byte[] data) throws FileNotFoundException, IOException, RemoteException;

    boolean containsFile(String name) throws RemoteException;

    List<Path> listFiles() throws RemoteException;

    String getPath();

    Path getParent();

}
