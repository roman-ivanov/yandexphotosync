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
        if (from.isDirectory()){
            for (Path i : from.listDirectories()) {
                sync(i, to.mkDirAndCD(i.getName()));
            }
            for (Path i: from.listFiles()){
                byte[] data = i.getData();
                if (!to.containsFile(i.getName())) {
                    to.uploadData(i.getName(), data);
                }
            }
        }else {
            byte[] data = from.getData();
            if (!to.containsFile(from.getName())) {
                to.uploadData(from.getName(), data);
            }
        }
    }
}
