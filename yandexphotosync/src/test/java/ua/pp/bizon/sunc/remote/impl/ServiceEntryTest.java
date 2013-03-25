package ua.pp.bizon.sunc.remote.impl;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import ua.pp.bizon.sunc.remote.Collection;
import ua.pp.bizon.sunc.remote.Entry;

public class ServiceEntryTest {

    protected static final byte[] buf_me;
    protected static final byte[] buf_albums;
    protected static final byte[] buf_photos;
    
    static {
        byte[] res = null, me = null, photos = null;
        try {
            res = IOUtils.toByteArray(new FileInputStream("src/test/resources/albums.xml"));
            me = IOUtils.toByteArray(new FileInputStream("src/test/resources/service.xml"));
            photos = IOUtils.toByteArray(new FileInputStream("src/test/resources/photos.xml"));
        } catch (IOException e) {
            LoggerFactory.getLogger(ServiceDocumentTest.class).error(e.getMessage(), e);
        }
        buf_albums = res;
        buf_me = me;
        buf_photos = photos;
    }

    @Test
    public void testGetCollectionPath() throws SAXException, IOException, ParserConfigurationException, RemoteException {
        ServiceEntry entry = new ServiceEntry("http://api-fotki.yandex.ru/api/me/") {
            protected java.io.InputStream getStream(String uri) {
                if (uri.equals("http://api-fotki.yandex.ru/api/me/")) {
                    return new ByteArrayInputStream(buf_me);
                } else if (uri.equals("http://api-fotki.yandex.ru/api/users/ivanov-roman-kr-ua/albums/")) {
                    return new ByteArrayInputStream(buf_albums);
                }
                if (uri.equals("http://api-fotki.yandex.ru/api/users/ivanov-roman-kr-ua/photos/")) {
                    return new ByteArrayInputStream(buf_albums);
                } else
                    fail(uri);
                return null;
            };
        };
        CollectionImpl collection = (CollectionImpl) entry.loadAlbums();
        assertNotNull(collection);
        System.out.println(collection);
        assertEquals(0, collection.unsortedMapByUrl.size());
        for (Entry e: collection){
            if (e.getParentUrl() != null){
                Entry x = null;
                for (Entry p: (Collection)e.getParent()){
                    if (p.equals(e)){
                        x = p;
                        break;
                    }
                }
                assertNotNull("parent should contain element " + e , x);
            }
        }
    }

}
