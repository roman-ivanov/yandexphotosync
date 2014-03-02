package ua.pp.bizon.sync.api.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class FileItem implements Item {
	protected static final String PREFIX = "file://";
	private final File file;

	public FileItem(String path) {
		file = new File(path);
	}

	protected FileItem(File f) {
		file = f;
	}

	@Override
	public boolean isDirectory() {
		return file.isDirectory();
	}

	@Override
	public Collection<Item> childrens() {
		File[] childrens = file.listFiles();
		Collection<Item> response = new ArrayList<>(childrens.length);
		for (File f: childrens){
			response.add(new FileItem(f));
		}
		return response;
	}

	@Override
	public String getFullPath() {
		return PREFIX + file.getPath();
	}

	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public String getChildPath(String name) {
		return PREFIX + new File(file, name).getPath();
	}

	@Override
	public Item getChild(String name) {
		return new FileItem(new File(file, name));
	}

	@Override
	public InputStream getInputStream() throws FileNotFoundException {
		return new FileInputStream(file);
	}

	@Override
	public OutputStream getOutputStream() throws FileNotFoundException {
		return new FileOutputStream(file);
	}

}
