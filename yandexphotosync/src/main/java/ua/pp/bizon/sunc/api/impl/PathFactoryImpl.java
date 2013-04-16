package ua.pp.bizon.sunc.api.impl;


import org.springframework.beans.factory.annotation.Autowired;

import ua.pp.bizon.sunc.api.Path;
import ua.pp.bizon.sunc.api.PathFactory;
import ua.pp.bizon.sunc.remote.impl.RemoteException;
import ua.pp.bizon.sunc.remote.impl.ServiceDocument;


public class PathFactoryImpl implements PathFactory {
    

    @Autowired
    private ServiceDocument root;
    
    @Override
    public Path create(String string) throws PathNotSupportedException {
        if (string.startsWith("yandex:"))
            try {
                return new YandexPath(root.getEntries().getOrCreatePath(string.substring(7)));
            } catch (RemoteException e) {
               throw new PathNotSupportedException(e.getMessage(), e);
            }
        else if (string.startsWith("file:"))
            return new LocalPath(string.substring(5));
        else 
            throw new PathNotSupportedException();
    }
}
