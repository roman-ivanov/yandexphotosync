package ua.pp.bizon.sunc.remote.impl;

import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

import ua.pp.bizon.sunc.remote.Album;
import ua.pp.bizon.sunc.remote.Collection;
import ua.pp.bizon.sunc.remote.Entry;

public class AlbumImpl extends AbstractEntry implements Entry, Album, Collection {

    private CollectionImpl albums = new CollectionImpl();
    private CollectionImpl photos = new CollectionImpl();

    public AlbumImpl(Node item) {
        super(item);
        albums.setElement(this);
    }

    public String toStringLog() {
        Transformer transformer;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(element);
            transformer.transform(source, result);

            String xmlString = result.getWriter().toString();
            return "album: " + xmlString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "AlbumImpl [collection=" + albums + ", " + super.toString() + "]";
    }

    @Override
    public Entry findEntryByName(String path) {
        return albums.findEntryByName(path);
    }

    @Override
    public Entry findEntryByUrl(String url) {
        return albums.findEntryByUrl(url);
    }

    @Override
    public Iterator<Entry> iterator() {
        return albums.iterator();
    }
    
    @Override
    public void addAll(Collection entries) {
         albums.addAll(entries);
    }

    @Override
    public void createPhoto(String name, byte[] data) throws RemoteException {
        YandexUtilsImpl.createPhoto(name, data, getId());
    }
    
    @Override
    public boolean containsPhoto(String name) {
        return photos.findEntryByName(name) != null;
    }

    @Override
    public Entry createAlbum(String name) throws RemoteException {
        try {
            Album newEntry = YandexUtilsImpl.createAlbum(name, getUrl());
            return newEntry;
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public Entry addEntry(Entry e) {
        return albums.addEntry(e);
    }

    @Override
    public Entry getOrCreatePath(String path) throws RemoteException {
        return albums.getOrCreatePath(path);
    }

    public static String createNewEntry(String name, String url) {
        if (url != null) {
            return "<entry xmlns=\"http://www.w3.org/2005/Atom\" xmlns:f=\"yandex:fotki\">\n" + "   <title>" + name
                    + "</title>\n" + "   <summary>" + name + "</summary>\n" + "   <link href=\"" + url
                    + "\" rel=\"album\" />\n" + "</entry>";
        } else
            return "<entry xmlns=\"http://www.w3.org/2005/Atom\" xmlns:f=\"yandex:fotki\">\n" + "   <title>" + name
                    + "</title>\n" + "   <summary>" + name + "</summary>\n" + "</entry>";
    }
}
