package ua.pp.bizon.sync.remote.api;

import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.LoggerFactory;

public class PhotoYandexUtils {

	public static boolean isDirectory(String username, String path) {
		LoggerFactory.getLogger(PhotoYandexUtils.class).debug("username = " + username + ", path = " + path);
		return false;
	}

	public static OutputStream getOutputStream(String username, String path) {
		LoggerFactory.getLogger(PhotoYandexUtils.class).debug("username = " + username + ", path = " + path);

		// TODO Auto-generated method stub
		return null;
	}

	public static InputStream getInputStream(String username, String path) {
		LoggerFactory.getLogger(PhotoYandexUtils.class).debug("username = " + username + ", path = " + path);
		// TODO Auto-generated method stub
		return null;
	}

}
