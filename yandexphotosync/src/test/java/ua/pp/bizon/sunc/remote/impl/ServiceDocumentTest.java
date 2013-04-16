package ua.pp.bizon.sunc.remote.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ServiceDocumentTest {

    @Test
    public void testListFiles() throws RemoteException {
        ServiceDocument serviceDocument = new ServiceDocument();
        serviceDocument.setEntry(new ServiceEntryTestImpl());
        assertNotNull(serviceDocument.getEntries());
        System.out.println(serviceDocument.getEntries());
    }


}
