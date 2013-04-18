package ua.pp.bizon.sunc.remote.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import ua.pp.bizon.sunc.remote.Album;

public class ServiceEntryTest {

    private ServiceEntry entry = new ServiceEntryTestImpl();

    @Test
    public void testLoader() throws RemoteException {
        RootEntry entry = this.entry.getRootEntry();
        assertNotNull(entry.dao.getAllEntries());
        assertEquals(1, entry.dao.getAllEntries().size());
        Album test = (Album) entry.dao.getAllEntries().get(0);
        assertEquals("test", test.getName());
        assertEquals("http://api-fotki.yandex.ru/api/users/test-sync/album/348161/", test.getUrl());
        assertEquals(2, test.listDirectories().size());
        assertEquals(2, test.listPhotos().size());
        assertEquals("http://api-fotki.yandex.ru/api/users/test-sync/photo/716471/",test.listPhotos().get(0).getUrl());
        assertEquals("http://api-fotki.yandex.ru/api/users/test-sync/photo/716470/",test.listPhotos().get(1).getUrl());
        Album t_1 = (Album) test.listDirectories().get(0);
        Album t_2 = (Album) test.listDirectories().get(1);
        assertEquals("http://api-fotki.yandex.ru/api/users/test-sync/album/348163/", t_1.getUrl());
        assertEquals("http://api-fotki.yandex.ru/api/users/test-sync/album/348162/", t_2.getUrl());
        assertEquals(1, t_1.listPhotos().size());
        assertEquals(1, t_2.listPhotos().size());
        assertEquals("http://api-fotki.yandex.ru/api/users/test-sync/photo/716469/",t_1.listPhotos().get(0).getUrl());
        assertEquals("http://api-fotki.yandex.ru/api/users/test-sync/photo/716468/",t_2.listPhotos().get(0).getUrl());
    }


}
