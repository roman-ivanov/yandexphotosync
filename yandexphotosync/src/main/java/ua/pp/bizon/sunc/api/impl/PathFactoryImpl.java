package ua.pp.bizon.sunc.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ua.pp.bizon.sunc.api.Path;
import ua.pp.bizon.sunc.api.PathFactory;
import ua.pp.bizon.sunc.remote.impl.RemoteException;
import ua.pp.bizon.sunc.remote.impl.ServiceDocument;

public class PathFactoryImpl implements PathFactory {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ServiceDocument root;

    @Override
    public Path create(String string) throws PathNotSupportedException {
        if (string.startsWith("yandex:"))
            try {
                Path p = new YandexPath(root.getRoot());
                for (String n : string.substring(7).split("/")) {
                    if (!n.trim().isEmpty()) {
                        if (p.getChildren(n) == null) {
                            logger.debug("creating directory: " + n + " in path " + p.getPath());
                            p.mkdir(n);
                        }
                        p = p.getChildren(n);
                    }
                }
                return p;
            } catch (RemoteException e) {
                throw new PathNotSupportedException(e.getMessage(), e);
            }
        else if (string.startsWith("file:"))
            return new LocalPath(string.substring(5));
        else
            throw new PathNotSupportedException();
    }
}
