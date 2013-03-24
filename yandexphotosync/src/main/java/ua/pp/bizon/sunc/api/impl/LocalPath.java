package ua.pp.bizon.sunc.api.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import ua.pp.bizon.sunc.api.Path;

public class LocalPath implements Path {

    private File path;

    public File getPath() {
        return path;
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
    public List<Path> listFiles() {
        LinkedList<Path> response = new LinkedList<Path>();
        for (File i : path.listFiles()) {
            response.add(new LocalPath(i));
        }
        return response;
    }

    @Override
    public String getName() {
        return path.getName();
    }

    @Override
    public Path mkDirAndCD(String name) {
        LocalPath response = new LocalPath(new File(path, name));
        response.path.mkdirs();
        return response;
    }
    
    @Override
    public boolean containsFile(String name) {
        return new File(path, name).isFile();
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
        return "LocalPath [path=" + path + ", isDirectory()=" + isDirectory() + ", getName()=" + getName() + "]";
    }
}