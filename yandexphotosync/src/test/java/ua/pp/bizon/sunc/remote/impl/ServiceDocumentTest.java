package ua.pp.bizon.sunc.remote.impl;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ua.pp.bizon.sunc.Sync;
import ua.pp.bizon.sunc.api.PathFactory;

public class ServiceDocumentTest {

    @Test
    public void testListFiles() throws RemoteException {
        ServiceDocument serviceDocument = new ServiceDocument();
        assertNotNull(serviceDocument.getPhotos());
    }

    @Test
    public void testGetAlbums() throws IOException, RemoteException {
        // assertNotNull(new ServiceDocument().getAlbums());
    }

}
