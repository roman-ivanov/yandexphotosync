package ua.pp.bizon.sync.api.impl;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import org.slf4j.LoggerFactory;

import ua.pp.bizon.sync.remote.api.PhotoYandexUtils;

public class PhotoYandexItem implements Item {
	
	private static final char DELIMETER = '\\';
	private String path;
	

	public PhotoYandexItem(String path) {
		LoggerFactory.getLogger(getClass()).debug("create new photo.yandex: " + path);
		this.path = path;
	}

	@Override
	public boolean isDirectory() {
		
		return PhotoYandexUtils.isDirectory(getUsername(), getPath());
	}

	protected String getPath() {
		return path.substring(path.indexOf(':') + 1);
	}

	protected String getUsername() {
		return path.substring(path.indexOf('@'), path.indexOf(':'));
	}

	@Override
	public Collection<Item> childrens() {
		System.out.println("childrens");
		return null;
	}

	@Override
	public String getFullPath() {
		return path;
	}

	@Override
	public String getName() {
		return path.substring(path.lastIndexOf(DELIMETER));
	}

	@Override
	public String getChildPath(String name) {
		return  path + DELIMETER + name;
	}

	@Override
	public Item getChild(String name) {
		return new PhotoYandexItem(getChildPath(name));
	}

	@Override
	public InputStream getInputStream() throws FileNotFoundException {
		return PhotoYandexUtils.getInputStream(getUsername(), getPath());
	}

	@Override
	public OutputStream getOutputStream() throws FileNotFoundException {
		return PhotoYandexUtils.getOutputStream(getUsername(), getPath());
	}

}
