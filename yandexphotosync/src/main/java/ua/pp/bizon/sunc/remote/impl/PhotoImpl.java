package ua.pp.bizon.sunc.remote.impl;

import org.w3c.dom.Node;

import ua.pp.bizon.sunc.remote.Entry;
import ua.pp.bizon.sunc.remote.Photo;

public class PhotoImpl extends AbstractEntry implements Entry, Photo {

	public PhotoImpl(Node item, ServiceDocument root) {
		super(item, root);
	}

    @Override
    public byte[] getData() {
        return null; // TODO add load file
    }

    @Override
    public String toString() {
        return "PhotoImpl[" + super.toString() + "]";
    }

    public Entry getEnclosingEntry() {
        return null;
    }

    public void setEnclosingEntry(Entry enclosingEntry) {
    }
}
