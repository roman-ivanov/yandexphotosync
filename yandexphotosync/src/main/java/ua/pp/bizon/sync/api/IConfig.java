package ua.pp.bizon.sync.api;

import java.util.List;

import ua.pp.bizon.sync.api.impl.SyncItem;

public interface IConfig {

	int getThreadCount();

	void read();

	List<SyncItem> getSyncItems();

}
