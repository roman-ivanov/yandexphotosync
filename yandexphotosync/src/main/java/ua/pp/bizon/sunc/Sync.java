package ua.pp.bizon.sunc;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.pp.bizon.sunc.api.Path;
import ua.pp.bizon.sunc.remote.impl.RemoteException;

public class Sync {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void sync(Path from, Path to) throws FileNotFoundException, IOException, RemoteException {
        logger.debug("sync from " + from + " to " + to);
        if (from.isDirectory()) {
            for (Path i : from.listDirectories()) {
                logger.debug("sync folder " + i.getName() + " in " + from.getPath());
                if (!to.containsFolder(i.getName())){
                    logger.debug("sunc: create new folder " + i.getName());
                    to.mkdir(i.getName());
                }
                sync(i, to.getChildren(i.getName()));
            }
            for (Path i : from.listFiles()) {
                logger.debug("sync file " + i.getName() + " in " + from.getPath());
                if (!to.containsFile(i.getName())) {
                    logger.debug("upload photo " + i.getName() + " to " + to);
                    to.uploadData(i.getName(), i.getData());
                }
            }
        } else {
            byte[] data = from.getData();
            if (!to.containsFile(from.getName())) {
                to.uploadData(from.getName(), data);
            }
        }
    }
}
