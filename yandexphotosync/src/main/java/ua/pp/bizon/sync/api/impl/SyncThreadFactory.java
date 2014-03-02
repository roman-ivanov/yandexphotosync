package ua.pp.bizon.sync.api.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ua.pp.bizon.sync.SyncApp;
import ua.pp.bizon.sync.api.ISyncItemController;
import ua.pp.bizon.sync.api.ISyncThreadFactory;

@Controller
public class SyncThreadFactory implements ISyncThreadFactory {
	
	@Autowired
	ISyncItemController controller;

	@Override
	public Runnable createSyncThread(SyncItem i, SyncApp service) {
		return new SyncWorker(i, service, controller);
	}

}
