package ua.pp.bizon.sunc.remote.impl;

import static org.junit.Assert.*;

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

    @Test
    public void testGetCollectionPath() throws SAXException, IOException, ParserConfigurationException, RemoteException {
        ServiceEntry entry = new ServiceEntryTestImpl();
        CollectionImpl collection = (CollectionImpl) entry.getEntries();
        assertNotNull(collection);
        System.out.println(collection.unsortedMapByUrl);
        
        assertEquals("should be empty: " + collection.unsortedMapByUrl, 0, collection.unsortedMapByUrl.size());
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
        collection = (CollectionImpl) entry.getEntries();
    }

}
