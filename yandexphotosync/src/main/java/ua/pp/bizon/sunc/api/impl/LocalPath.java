package ua.pp.bizon.sunc.api.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import ua.pp.bizon.sunc.api.Path;
import ua.pp.bizon.sunc.remote.impl.RemoteException;

public class LocalPath implements Path {

    private File path;

    @Override
    public Path getParent() {
        return new LocalPath(path.getParentFile());
    }

    public String getPath() {
        return "file:" + path.getPath();
    }

    public LocalPath(String substring) {
        this.path = new File(substring);
    }

    private LocalPath(File path) {
        this.path = path;
    }

    @Override
    public boolean isDirectory() {
        return path.isDirectory();
    }

    @Override
    public List<Path> listDirectories() {
        LinkedList<Path> response = new LinkedList<Path>();
        for (File i : path.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        })) {
            response.add(new LocalPath(i));
        }
        return response;
    }

    @Override
    public List<Path> listFiles() throws RemoteException {
        LinkedList<Path> response = new LinkedList<Path>();
        for (File i : path.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(".jpg");
            }
        })) {
            response.add(new LocalPath(i));
        }
        return response;
    }

    @Override
    public List<Path> listDirectoriesAndFiles() {
        LinkedList<Path> response = new LinkedList<Path>();
        for (File i : path.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory() || (pathname.isFile() && pathname.getName().endsWith(".jpg"));
            }
        })) {
            response.add(new LocalPath(i));
        }
        return response;
    }

    @Override
    public String getName() {
        return path.getName();
    }

    @Override
    public void mkdir(String name) {
        new File(path, name).mkdirs();
    }

    @Override
    public Path getChildren(String name) {
        return new LocalPath(new File(path, name));
    }

    @Override
    public boolean containsFile(String name) {
        return new File(path, name).isFile();
    }

    @Override
    public boolean containsFolder(String name) {
        return new File(path, name).isDirectory();
    }

    @Override
    public byte[] getData() throws FileNotFoundException, IOException {
        return IOUtils.toByteArray(new FileInputStream(path));
    }

    @Override
    public void uploadData(String name, byte[] data) throws FileNotFoundException, IOException {
        IOUtils.write(data, new FileOutputStream(new File(path, name)));
    }

    @Override
    public String toString() {
        return "LocalPath [path=" + path + ", isDirectory()=" + isDirectory() + ", getName()=" + getName() + ", size="
                + path.length() + "]";
    }
}
