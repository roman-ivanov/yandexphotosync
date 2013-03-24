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
        logger.trace("sync from " + from + " to " + to);
        if (from.isDirectory())
            for (Path i : from.listFiles()) {
                sync(i, to.mkDirAndCD(i.getName()));
            }
        else {
            byte[] data = from.getData();
            if (!to.containsFile(from.getName())) {
                to.uploadData(from.getName(), data);
            }
        }
    }
}
