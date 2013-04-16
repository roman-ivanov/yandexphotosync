package ua.pp.bizon.sunc.remote.impl;

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

final class ServiceEntryTestImpl extends ServiceEntry {
    private static final byte[] buf_me;
    private static final byte[] buf_albums;
    private static final byte[] buf_photos;

    static {
        byte[] res = null, me = null, photos = null;
        try {
            res = IOUtils.toByteArray(ServiceEntryTest.class.getResourceAsStream("/albums.xml"));
            me = IOUtils.toByteArray(ServiceEntryTest.class.getResourceAsStream("/service.xml"));
            photos = IOUtils.toByteArray(ServiceEntryTest.class.getResourceAsStream("/photos.xml"));
        } catch (IOException e) {
            LoggerFactory.getLogger(ServiceDocumentTest.class).error(e.getMessage(), e);
        }
        buf_albums = res;
        buf_me = me;
        buf_photos = photos;
    }

    ServiceEntryTestImpl() {
        super("");
    }

    public java.io.InputStream getStream(String uri) {
        if (uri.equals("http://api-fotki.yandex.ru/api/me/") || uri.isEmpty()) {
            return new ByteArrayInputStream(buf_me);
        } else if (uri.equals("http://api-fotki.yandex.ru/api/users/test-sync/albums/")) {
            return new ByteArrayInputStream(buf_albums);
        }
        if (uri.equals("http://api-fotki.yandex.ru/api/users/test-sync/photos/")) {
            return new ByteArrayInputStream(buf_photos);
        }
        fail(uri);
        throw new RuntimeException();
    }
}