package ua.pp.bizon.sync.api.impl;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

public interface Item {

	public abstract boolean isDirectory();

	public abstract Collection<Item> childrens();

	public abstract String getFullPath();

	public abstract String getName();

	public abstract String getChildPath(String name);

	public abstract Item getChild(String name);

	public abstract InputStream getInputStream() throws FileNotFoundException;

	public abstract OutputStream getOutputStream() throws FileNotFoundException;

}
