package ua.pp.bizon.sunc.remote.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import ua.pp.bizon.sunc.remote.Album;
import ua.pp.bizon.sunc.remote.Collection;
import ua.pp.bizon.sunc.remote.Entry;

public class ServiceEntryTest {

    @Test
    public void testGetCollectionPath() throws SAXException, IOException, ParserConfigurationException, RemoteException {
        ServiceEntry entry = new ServiceEntryTestImpl();
        CollectionImpl collection = (CollectionImpl) entry.getEntries();
        assertNotNull(collection);

        assertEquals("should be empty: " + collection.unsortedMapByUrl, 0, collection.unsortedMapByUrl.size());
        for (Entry e : collection) {
            if (e.getParentUrl() != null) {
                Entry x = null;
                for (Entry p : (Collection) e.getParent()) {
                    if (p.equals(e)) {
                        x = p;
                        break;
                    }
                }
                if (x == null && e.getParent() instanceof Album)
                for (Entry p : ((Album) e.getParent()).getPhotosIterable()) {
                    if (p.equals(e)) {
                        x = p;
                        break;
                    }
                }
                assertNotNull("parent should contain element " + e, x);
            }
        }
        collection = (CollectionImpl) entry.getEntries();
    }

    @Test
    public void testChekcSync() throws Exception {
        Collection impl = (Collection) new ServiceEntryTestImpl().getEntries();
        Entry e = impl.findEntryByUrl("http://api-fotki.yandex.ru/api/users/test-sync/photo/716468/");
        System.out.println(e.getParent());
        assertTrue(((Album) e.getParent()).containsPhoto(e.getName()));
    }

}
