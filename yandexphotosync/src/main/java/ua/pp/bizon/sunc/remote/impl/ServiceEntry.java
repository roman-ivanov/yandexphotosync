package ua.pp.bizon.sunc.remote.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ua.pp.bizon.sunc.remote.Album;
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
    
    private RootEntry rootEntry;
    private Logger logger = LoggerFactory.getLogger(getClass());


    void load() throws SAXException, IOException, ParserConfigurationException, RemoteException {
        InputStream xml = getStream(uri);
        xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xml);
        albums = getCollectionPath("album-list");
        photos = getCollectionPath("photo-list");
        rootEntry = new RootEntry(this);
        rootEntry.addAll(parseEntries(getStream(albums)));
        rootEntry.addAll(parseEntries(getStream(photos)));
    }

    protected InputStream getStream(String uri) throws HttpException, IOException {
        return httpUtil.getStream(uri);
    }
    
    public RootEntry getRootEntry() throws RemoteException {
        if (rootEntry == null) {
            try {
                logger.trace("load started");
                load();
                logger.trace("load ended");
            } catch (RemoteException e) {
                throw e;
            } catch (Exception e) {
               throw new RemoteException(e.getMessage(), e); 
            } 
        }
        return rootEntry;
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
        logger.error("collection not found in: " + xmlDoc);
        return null;
    }

    protected List<Entry> parseEntries(InputStream stream) throws SAXException, IOException, ParserConfigurationException,
            RemoteException {
        Document xmlAlbums = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
        NodeList list = xmlAlbums.getElementsByTagName("entry");
        logger.trace("parseEntries: found " + list.getLength() + " elements");
        List<Entry> entries = new LinkedList<Entry>();
        for (int i = 0; i < list.getLength(); i++) {
            entries.add(parseEntry(list.item(i)));
        }
        list = xmlAlbums.getDocumentElement().getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals("link") && list.item(i).getAttributes().getNamedItem("rel") != null
                    && list.item(i).getAttributes().getNamedItem("rel").getNodeValue().equals("next")) {
                String nextUrl = list.item(i).getAttributes().getNamedItem("href").getNodeValue();
                logger.trace("parseEntries: found " + nextUrl + " url");
                entries.addAll(parseEntries(getStream(nextUrl)));
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
                logger.trace("parseEntry: photo "+ id + " found");
                return new PhotoImpl(item, root);
            } else if (ids[4].equals("album")) {
                logger.trace("parseEntry: album "+ id + " found");
                return new AlbumImpl(item, root);
            }
        }
        throw new RemoteException("id not found or can't be identified");
    }

    protected Node getXmlNode() {
        return xmlDoc.getDocumentElement();
    }

    public Album createAlbum(String name, String url) throws RemoteException {
        logger.trace("create album: name=" + name + ", url=" + url);
        try {
            return (Album) rootEntry.addEntry(
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
            return (Photo) parseEntries(httpUtil.postData(photos, name, data, id)).iterator().next();
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    public InputStream getData(String imageLink) throws HttpException, IOException {
        return httpUtil.getStream(imageLink);
    }

}
