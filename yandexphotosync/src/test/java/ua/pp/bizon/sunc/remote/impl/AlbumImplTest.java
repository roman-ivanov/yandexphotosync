package ua.pp.bizon.sunc.remote.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class AlbumImplTest {

    private final static String nodeText = "<entry>\n"
            + "    <id>urn:yandex:fotki:test-sync:album:348164</id>\n"
            + "    <author>\n"
            + "      <name>test-sync</name>\n"
            + "      <f:uid>205099098</f:uid>\n"
            + "    </author>\n"
            + "    <title>Неразобранное в test</title>\n"
            + "    <link href=\"http://api-fotki.yandex.ru/api/users/test-sync/album/348164/\" rel=\"self\" />\n"
            + "    <link href=\"http://api-fotki.yandex.ru/api/users/test-sync/album/348164/\" rel=\"edit\" />\n"
            + "    <link href=\"http://api-fotki.yandex.ru/api/users/test-sync/album/348164/photos/\" rel=\"photos\" />\n"
            + "    <link href=\"http://api-fotki.yandex.ru/api/users/test-sync/photo/716470/\" rel=\"cover\" />\n"
            + "    <f:img height=\"75\" href=\"http://img-fotki.yandex.ru/get/4118/205099098.1/0_aeeb6_4730fe52_XXS\" size=\"XXS\" width=\"75\" />\n"
            + "    <f:img height=\"150\" href=\"http://img-fotki.yandex.ru/get/4118/205099098.1/0_aeeb6_4730fe52_S\" size=\"S\" width=\"148\" />\n"
            + "    <link href=\"http://api-fotki.yandex.ru/api/users/test-sync/album/348164/photos.ymapsml/\" rel=\"ymapsml\" />\n"
            + "    <link href=\"http://fotki.yandex.ru/users/test-sync/album/348164/\" rel=\"alternate\" />\n"
            + "    <published>2013-03-30T13:38:18Z</published>\n"
            + "    <app:edited>2013-03-30T13:38:23Z</app:edited>\n" + "    <updated>2013-03-30T13:38:23Z</updated>\n"
            + "    <f:protected value=\"false\" />\n" + "    <f:image-count value=\"2\" />\n"
            + "    <link href=\"http://api-fotki.yandex.ru/api/users/test-sync/album/348161/\" rel=\"album\" />\n"
            + "  </entry>";

    private Node node;

    @Before
    public void setUp() throws Exception {
        node = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new InputSource(new StringReader(nodeText))).getDocumentElement();
    }

    @Test
    public void testAlbumImpl() {
        new AlbumImpl(node, null);
    }

    @Test
    public void testFindEntryByName() {
        AlbumImpl a = new AlbumImpl(node, null);
        assertEquals(null, a.findEntryByName("dd"));
    }

    @Test
    public void testGetLink() {
        assertNotNull(new AlbumImpl(node, null).getLink("self"));
        assertNull(new AlbumImpl(node, null).getLink("self1"));
        assertEquals("http://api-fotki.yandex.ru/api/users/test-sync/album/348164/",
                new AlbumImpl(node, null).getLink("self"));
    }

    @Test
    public void testGetParentUrl() {
        assertNotNull(new AlbumImpl(node, null).getParentUrl());
        assertEquals("http://api-fotki.yandex.ru/api/users/test-sync/album/348161/",
                new AlbumImpl(node, null).getParentUrl());
    }

    @Test
    public void testGetUrl() {
        assertNotNull(new AlbumImpl(node, null).getUrl());
        assertEquals("http://api-fotki.yandex.ru/api/users/test-sync/album/348164/", new AlbumImpl(node, null).getUrl());
    }

    @Test
    public void testGetId() {
        assertNotNull(new AlbumImpl(node, null).getId());
        assertEquals("urn:yandex:fotki:test-sync:album:348164", new AlbumImpl(node, null).getId());
    }

}
