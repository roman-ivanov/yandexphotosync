package ua.pp.bizon.sunc.remote.impl;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ua.pp.bizon.sunc.remote.Album;
import ua.pp.bizon.sunc.remote.Collection;
import ua.pp.bizon.sunc.remote.Entry;
import ua.pp.bizon.sunc.remote.Photo;

public class ServiceEntry {

	public ServiceEntry(String uri) throws SAXException, IOException,
			ParserConfigurationException {
		loadServiceDocument(uri);
	}

	private String albums;
	private String photos;
	private Document xmlDoc;

	private void loadServiceDocument(String uri) throws SAXException,
			IOException, ParserConfigurationException {
		InputStream xml = getStream(uri);
		xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(xml);
		albums = getCollectionPath("album-list");
		photos = getCollectionPath("photo-list");
	}

	protected InputStream getStream(String uri) throws HttpException,
			IOException {
		return HttpUtil.getStream(uri);
	}

	protected String getCollectionPath(String string) {
		NodeList list = xmlDoc.getElementsByTagName("collection");
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getAttributes().getNamedItem("id").getNodeValue()
					.equals(string)) {
				return list.item(i).getAttributes().getNamedItem("href")
						.getNodeValue();
			}
		}
		list = xmlDoc.getElementsByTagName("app:collection");
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getAttributes().getNamedItem("id").getNodeValue()
					.equals(string)) {
				return list.item(i).getAttributes().getNamedItem("href")
						.getNodeValue();
			}
		}
		LoggerFactory.getLogger(getClass()).error(
				"collection not found in: " + xmlDoc);
		return null;
	}

	public Collection loadAlbums() throws SAXException, IOException,
			ParserConfigurationException, RemoteException {
		return getEntries(getStream(albums));
	}

	protected  Collection getEntries(InputStream stream) throws SAXException,
			IOException, ParserConfigurationException, RemoteException {
		Document xmlAlbums = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(stream);
		NodeList list = xmlAlbums.getElementsByTagName("entry");
		Collection entries = new CollectionImpl();
		for (int i = 0; i < list.getLength(); i++) {
			entries.addEntry(parseEntry(list.item(i)));
		}
		list = xmlAlbums.getDocumentElement().getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
		    if (list.item(i).getNodeName().equals("link") && 
		            list.item(i).getAttributes().getNamedItem("rel") != null &&
		            list.item(i).getAttributes().getNamedItem("rel").getNodeValue().equals("next")){
		        String nextUrl = list.item(i).getAttributes().getNamedItem("href").getNodeValue();
		        entries.addAll(getEntries(getStream(nextUrl)));
		    }
        }
		return entries;
	}

	private static Entry parseEntry(Node item) throws RemoteException {
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
				return new PhotoImpl(item);
			} else if (ids[4].equals("album")) {
				return new AlbumImpl(item);
			}
		}
		throw new RemoteException("id not found or can't be identified");
	}

	public Collection loadPhotos() throws HttpException, SAXException,
			IOException, ParserConfigurationException, RemoteException {
		return getEntries(getStream(photos));
	}

	public Node getXmlNode() {
		return xmlDoc.getDocumentElement();
	}

    public Album createAlbum(String name, String url) throws RemoteException {
        try {
            return (Album) getEntries(HttpUtil.postEntry(albums, AlbumImpl.createNewEntry(name, url))).iterator().next();
        }catch(RemoteException e){
            throw e;
        }catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            throw new RemoteException(e.getMessage(), e);
        } 
    }

    public Photo createPhoto(String name, byte[] data, String id) throws RemoteException {
        try {
            return (Photo) getEntries(HttpUtil.postData(photos, name, data, id)).iterator().next();
        }catch(RemoteException e){
            throw e;
        }catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

}
