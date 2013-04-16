package ua.pp.bizon.sunc.remote.impl;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ua.pp.bizon.sunc.remote.Album;
import ua.pp.bizon.sunc.remote.Collection;
import ua.pp.bizon.sunc.remote.Entry;
import ua.pp.bizon.sunc.remote.Photo;
import ua.pp.bizon.sunc.remote.api.HttpUtil;

public class ServiceEntry {

    @Autowired
    private HttpUtil httpUtil;

    public ServiceEntry(String uri) {
        this.uri = uri;
    }

    protected String albums;
    protected String photos;
    private Document xmlDoc;
    private String uri;
    @Autowired
    private ServiceDocument root;
    private Collection collection;

    void load() throws SAXException, IOException, ParserConfigurationException, RemoteException {
        InputStream xml = getStream(uri);
        xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xml);
        albums = getCollectionPath("album-list");
        photos = getCollectionPath("photo-list");
        collection = new CollectionImpl(root);
        collection.setEnclosingEntry(new RootEntry(collection));
        collection.addAll(getEntries(getStream(albums)));
        collection.addAll(getEntries(getStream(photos)));
    }

    protected InputStream getStream(String uri) throws HttpException, IOException {
        return httpUtil.getStream(uri);
    }

    public Collection getEntries() throws RemoteException {
        if (collection == null) {
            try {
                load();
            } catch (RemoteException e) {
                throw e;
            } catch (Exception e) {
               throw new RemoteException(e.getMessage(), e); 
            } 
        }
        return collection;
    }

    public HttpUtil getHttpUtil() {
        return httpUtil;
    }

    public void setHttpUtil(HttpUtil httpUtil) {
        this.httpUtil = httpUtil;
    }

    protected String getCollectionPath(String string) {
        NodeList list = xmlDoc.getElementsByTagName("collection");
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(string)) {
                return list.item(i).getAttributes().getNamedItem("href").getNodeValue();
            }
        }
        list = xmlDoc.getElementsByTagName("app:collection");
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(string)) {
                return list.item(i).getAttributes().getNamedItem("href").getNodeValue();
            }
        }
        LoggerFactory.getLogger(getClass()).error("collection not found in: " + xmlDoc);
        return null;
    }

    protected Collection getEntries(InputStream stream) throws SAXException, IOException, ParserConfigurationException,
            RemoteException {
        Document xmlAlbums = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
        NodeList list = xmlAlbums.getElementsByTagName("entry");
        Collection entries = new CollectionImpl(root);
        for (int i = 0; i < list.getLength(); i++) {
            entries.addEntry(parseEntry(list.item(i)));
        }
        list = xmlAlbums.getDocumentElement().getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals("link") && list.item(i).getAttributes().getNamedItem("rel") != null
                    && list.item(i).getAttributes().getNamedItem("rel").getNodeValue().equals("next")) {
                String nextUrl = list.item(i).getAttributes().getNamedItem("href").getNodeValue();
                entries.addAll(getEntries(getStream(nextUrl)));
            }
        }
        return entries;
    }

    private Entry parseEntry(Node item) throws RemoteException {
        NodeList list = item.getChildNodes();
        String id = null;
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals("id")) {
                id = list.item(i).getTextContent();
                break;
            }
        }
        if (id != null) {
            String[] ids = id.split(":");
            if (ids[4].equals("photo")) {
                return new PhotoImpl(item, root);
            } else if (ids[4].equals("album")) {
                return new AlbumImpl(item, root);
            }
        }
        throw new RemoteException("id not found or can't be identified");
    }

    protected Node getXmlNode() {
        return xmlDoc.getDocumentElement();
    }

    public Album createAlbum(String name, String url) throws RemoteException {
        try {
            return (Album) collection.addEntry(
                    parseEntry(
                            DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                                    httpUtil.postEntry(albums, AlbumImpl.createNewEntry(name, url))
                                    ).getFirstChild()));
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    public Photo createPhoto(String name, byte[] data, String id) throws RemoteException {
        try {
            return (Photo) getEntries(httpUtil.postData(photos, name, data, id)).iterator().next();
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

}
