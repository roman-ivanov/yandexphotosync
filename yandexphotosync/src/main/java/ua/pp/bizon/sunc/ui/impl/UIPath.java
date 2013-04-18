package ua.pp.bizon.sunc.ui.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ua.pp.bizon.sunc.api.Path;
import ua.pp.bizon.sunc.remote.impl.RemoteException;

public class UIPath implements Path {

    public void mkdir(String name) throws RemoteException {
        delegate.mkdir(name);
    }

    private Path delegate;

    public UIPath(Path create) {
        this.delegate = create;
    }

    public boolean isDirectory() {
        return delegate.isDirectory();
    }

    public List<Path> listDirectories() throws RemoteException {
        return delegate(delegate.listDirectories());
    }
    @Override
    public List<Path> listDirectoriesAndFiles() {
        return delegate(delegate.listDirectoriesAndFiles());
    }

    public String getName() {
        return delegate.getName();
    }

    public byte[] getData() throws FileNotFoundException, IOException, RemoteException {
        return delegate.getData();
    }

    public void uploadData(String name, byte[] data) throws FileNotFoundException, IOException, RemoteException {
        delegate.uploadData(name, data);
    }

    public boolean containsFile(String name) throws RemoteException {
        return delegate.containsFile(name);
    }

    public boolean containsFolder(String name) {
        return delegate.containsFolder(name);
    }

    public Path getChildren(String name) throws RemoteException {
        return new UIPath(delegate.getChildren(name));
    }

    public List<Path> listFiles() throws RemoteException {
        return delegate(delegate.listFiles());
    }

    protected List<Path> delegate(List<Path> from) {
        List<Path> ret = new ArrayList<Path>();
        for (Path i : from) {
            ret.add(new UIPath(i));
        }
        return ret;
    }

    @Override
    public String toString() {
        return getName();
    }
    
    @Override
    public String getPath() {
        return delegate.getPath();
    }

    @Override
    public Path getParent() {
        return new UIPath(delegate.getParent());
    }
}
