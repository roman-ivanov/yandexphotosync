package ua.pp.bizon.sunc;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ua.pp.bizon.sunc.api.Path;
import ua.pp.bizon.sunc.api.PathFactory;
import ua.pp.bizon.sunc.api.impl.PathNotSupportedException;
import ua.pp.bizon.sunc.remote.impl.RemoteException;

public class SyncTest {

    @Test
    public void testSync() throws FileNotFoundException, IOException, RemoteException, BeansException, PathNotSupportedException {
         final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.scan("ua.pp.bizon.sunc.test");
        applicationContext.refresh();
        Path from = applicationContext.getBean(PathFactory.class).create("file:src/test/resources/synctest");
        Path to =  applicationContext.getBean(PathFactory.class).create("yandex:/");
        Sync sync = new Sync();
        sync.sync(from, to);
        sync.sync(to, from);
        applicationContext.close();
    }

}
