package ua.pp.bizon.sunc.remote.impl;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import ua.pp.bizon.sunc.remote.Album;
import ua.pp.bizon.sunc.remote.Collection;
import ua.pp.bizon.sunc.remote.Entry;
import ua.pp.bizon.sunc.remote.Photo;
import ua.pp.bizon.sunc.remote.api.EntryDAO;

public class AlbumImpl extends AbstractEntry implements Entry, Album, Collection {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private EntryDAO entryDAO = new EntryDAOImpl();
    private Album photosUnsorted = null;

    public AlbumImpl(Node item, ServiceDocument root) {
        super(item, root);
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
    public Entry findEntryByName(String path) {
        Entry res = entryDAO.findEntryByName(path);
        if (res == null && photosUnsorted != null){
            res = photosUnsorted.findEntryByName(path);
        }
        return res;
    }

    @Override
    public Entry findEntryByUrl(String url) {
        Entry res = entryDAO.findEntryByUrl(url);
        if (res == null && photosUnsorted != null){
            res = photosUnsorted.getUrl().equals(url) ? photosUnsorted : photosUnsorted.findEntryByUrl(url);
        }
        return res;    
    }

    @Override
    public Iterator<Entry> iterator() {
        return photosUnsorted == null ? entryDAO.getAllEntries().iterator() : split(photosUnsorted.listPhotos(), entryDAO.getAllEntries()).iterator();
    }

    private List<Entry> split(List<Entry> listPhotos, List<Entry> allEntries) {
        List<Entry> result = new LinkedList<Entry>();
        result.addAll(listPhotos);
        result.addAll(allEntries);
        return result;
    }

    @Override
    public List<Entry> listDirectories() {
        return entryDAO.listDirectories();
    }

    @Override
    public void addAll(List<Entry> entries) {
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
        return photosUnsorted == null ? entryDAO.findEntryByName(name) != null && entryDAO.findEntryByName(name) instanceof Photo : photosUnsorted.containsPhoto(name);
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
        if (e.getName().equals("Неразобранное в " + getName()) && e instanceof Album) {
            photosUnsorted = (Album) e;
            logger.debug("found unsorted");
            return e;
        } else {
            e.setParent(this);
            return entryDAO.save(e);

        }
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
    public List<Entry> listPhotos() {
        return photosUnsorted == null ? entryDAO.listPhotos() : photosUnsorted.listPhotos();
    }

    public boolean isEmpty() {
        return entryDAO.isEmpty() || (photosUnsorted != null && photosUnsorted.isEmpty());
    }

    @Override
    public String toString() {
        return "AlbumImpl [name=" + getName() + ", path=" + getPath() +  ",entryDAO=" + entryDAO + ", photosUnsorted=" + photosUnsorted + "]";
    }
    
    
}
