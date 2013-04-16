package ua.pp.bizon.sunc.remote.impl;

import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import ua.pp.bizon.sunc.remote.Album;
import ua.pp.bizon.sunc.remote.Collection;
import ua.pp.bizon.sunc.remote.Entry;
import ua.pp.bizon.sunc.remote.Photo;

public class AlbumImpl extends AbstractEntry implements Entry, Album, Collection {

    private Collection albums = new CollectionImpl(root);
    private Collection photos = new CollectionImpl(root);

    public AlbumImpl(Node item, ServiceDocument root) {
        super(item, root);
        albums.setEnclosingEntry(this);
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
        return "AlbumImpl [albums=" + albums + ", photos=" + photos + ", " + super.toString() + "]";
    }

    @Override
    public Entry findEntryByName(String path) {
        return albums.isEmpty() ? photos.findEntryByName(path) : albums.findEntryByName(path);
    }

    @Override
    public Entry findEntryByUrl(String url) {
        return albums.isEmpty() ?photos.findEntryByUrl(url) : albums.findEntryByUrl(url);
    }

    @Override
    public Iterator<Entry> iterator() {
        return albums.iterator();
    }

    @Override
    public void addAll(Collection entries) {
        for (Entry e : entries) {
            addEntry(e);
        }
    }

    @Override
    public void createPhoto(String name, byte[] data) throws RemoteException {
        root.createPhoto(name, data, getId());
    }

    @Override
    public boolean containsPhoto(String name) {
        return photos.findEntryByName(name) != null;
    }

    @Override
    public Album createAlbum(String name) throws RemoteException {
        try {
            Album newEntry = root.createAlbum(name, getUrl());
            return newEntry;
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public Entry addEntry(Entry e) {
        if (e instanceof Photo) {
            return photos.addEntry(e);
        } else {
            if (e.getName().equals("Неразобранное в " + getName()) && e instanceof Collection){
                photos = (Collection) e;
                LoggerFactory.getLogger(getClass()).debug("found unsorted");
            }
            return albums.addEntry(e);
        }
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

    @Override
    public Iterable<Entry> getPhotosIterable() {
        return photos;
    }

    public boolean isEmpty() {
        return false;
    }

    public Entry getEnclosingEntry() {
        return null;
    }

    public void setEnclosingEntry(Entry enclosingEntry) {
    }
}
