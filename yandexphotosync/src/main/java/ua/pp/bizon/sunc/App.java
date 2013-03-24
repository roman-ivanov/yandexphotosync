package ua.pp.bizon.sunc;

import ua.pp.bizon.sunc.api.impl.PathFactory;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws Exception {
		new Sync().sync(PathFactory.create("file:/Users/roman/Dropbox/Photos"),
				PathFactory.create("yandex:/Dropbox/photos"));
		System.out.println("done");
	}
}
