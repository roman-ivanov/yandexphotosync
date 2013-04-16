package ua.pp.bizon.sunc.ui.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ua.pp.bizon.sunc.api.Path;
import ua.pp.bizon.sunc.remote.impl.RemoteException;

public class UIPath implements Path {

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

    public String getName() {
        return delegate.getName();
    }

    public Path mkDirAndCD(String name) throws RemoteException {
        return new UIPath(delegate.mkDirAndCD(name));
    }

    public byte[] getData() throws FileNotFoundException, IOException {
        return delegate.getData();
    }

    public void uploadData(String name, byte[] data) throws FileNotFoundException, IOException, RemoteException {
        delegate.uploadData(name, data);
    }

    public boolean containsFile(String name) throws RemoteException {
        return delegate.containsFile(name);
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
