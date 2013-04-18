package ua.pp.bizon.sunc.remote.impl;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Node;

import ua.pp.bizon.sunc.remote.Entry;
import ua.pp.bizon.sunc.remote.Photo;

public class PhotoImpl extends AbstractEntry implements Entry, Photo {

	public PhotoImpl(Node item, ServiceDocument root) {
		super(item, root);
	}

    @Override
    public byte[] getData() throws RemoteException {
        try {
            return IOUtils.toByteArray(root.getServiceEntry().getData(getImageLink("orig")));
        } catch ( IOException e) {
           throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public String toString() {
        return "PhotoImpl[" + super.toString() + "]";
    }
}
