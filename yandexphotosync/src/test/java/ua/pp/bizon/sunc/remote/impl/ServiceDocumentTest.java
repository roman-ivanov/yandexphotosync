package ua.pp.bizon.sunc.remote.impl;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

public class ServiceDocumentTest {

    @Test
    public void testListFiles() throws RemoteException {
        ServiceDocument serviceDocument = new ServiceDocument();
        serviceDocument.setEntry(new ServiceEntryTestImpl());
        assertNotNull(serviceDocument.getPhotos());
        System.out.println(serviceDocument.getPhotos());
    }

    @Test
    public void testGetAlbums() throws IOException, RemoteException {
        ServiceDocument serviceDocument = new ServiceDocument();
        serviceDocument.setEntry(new ServiceEntryTestImpl());
         assertNotNull(serviceDocument.getAlbums());
    }

}
