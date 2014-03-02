package ua.pp.bizon.sync.api;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import ua.pp.bizon.sunc.ui.Direction;
import ua.pp.bizon.sync.api.impl.SyncItem;

public interface ISyncItemController {

	boolean isDirectory(SyncItem item);

	List<SyncItem> getChilds(SyncItem item);

	void createDirectory(SyncItem item);

	OutputStream getOutputStream(SyncItem item, Direction to) throws FileNotFoundException;

	InputStream getInputStream(SyncItem item, Direction from) throws FileNotFoundException;

}
