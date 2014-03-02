package ua.pp.bizon.sync.api.impl;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import ua.pp.bizon.sunc.ui.Direction;
import ua.pp.bizon.sync.api.ISyncItemController;

@Controller
public class SyncItemController implements ISyncItemController {

	@Override
	public boolean isDirectory(SyncItem item) {
		return createItem(item.getFrom()).isDirectory();
	}

	protected Item createItem(String from) {
		LoggerFactory.getLogger(getClass()).debug("create item: " + from);
		if (from.startsWith("file:"))
			return new FileItem(from.substring(7));
		else if (from.startsWith("photo.yandex"))
			return new PhotoYandexItem(from);
		else
			throw new UnsupportedOperationException("unknown type: " + from);
	}

	@Override
	public List<SyncItem> getChilds(SyncItem item) {
		Item from = createItem(item.getFrom());
		Item to = createItem(item.getTo());
		List<SyncItem> responce = new ArrayList<>();
		for (Item i : from.childrens()) {
			responce.add(new CachedSyncItem(i, to.getChild(i.getName())));
		}
		return responce;
	}

	@Override
	public void createDirectory(SyncItem item) {
		LoggerFactory.getLogger(getClass()).debug("create directory: " + item);
	}

	@Override
	public InputStream getInputStream(SyncItem syncitem, Direction from) throws FileNotFoundException {
		Item item = null;
		if (syncitem instanceof CachedSyncItem) {
			item = Direction.FROM == from ? ((CachedSyncItem)syncitem).getFromItem() : 
				((CachedSyncItem) syncitem).getToItem();
		} else {
			 item = Direction.FROM == from ? createItem(syncitem.getFrom()) : createItem(syncitem.getTo());
		}
		return item.getInputStream();
	}

	@Override
	public OutputStream getOutputStream(SyncItem syncitem, Direction from) throws FileNotFoundException {
		Item item = null;
		if (syncitem instanceof CachedSyncItem) {
			item = Direction.FROM == from ? ((CachedSyncItem)syncitem).getFromItem() : 
				((CachedSyncItem) syncitem).getToItem();
		} else {
			 item = Direction.FROM == from ? createItem(syncitem.getFrom()) : createItem(syncitem.getTo());
		}
		return item.getOutputStream();
	}
}
