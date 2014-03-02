package ua.pp.bizon.sync.api;

import ua.pp.bizon.sync.SyncApp;
import ua.pp.bizon.sync.api.impl.SyncItem;

public interface ISyncThreadFactory {

	Runnable createSyncThread(SyncItem i, SyncApp syncApp);

}
