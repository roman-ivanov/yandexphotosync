package ua.pp.bizon.sunc.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import ua.pp.bizon.sunc.remote.impl.RemoteException;

public interface Path {

    boolean isDirectory();

    List<Path> listDirectories() throws RemoteException;

    String getName();

    byte[] getData() throws FileNotFoundException, IOException, RemoteException;

    void uploadData(String name, byte[] data) throws FileNotFoundException, IOException, RemoteException;

    boolean containsFile(String name) throws RemoteException;

    List<Path> listFiles() throws RemoteException;

    String getPath();

    Path getParent();

    boolean containsFolder(String name);

    Path getChildren(String name) throws RemoteException;

    void mkdir(String name) throws RemoteException;

    List<Path> listDirectoriesAndFiles();

}
