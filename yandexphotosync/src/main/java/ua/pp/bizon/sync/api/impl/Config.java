package ua.pp.bizon.sync.api.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ua.pp.bizon.sync.api.IConfig;

@Repository
public class Config implements IConfig {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	private int threadCount = 10;
	private List<SyncItem> syncItems= new ArrayList<>();
	@Override
	public int getThreadCount() {
		return threadCount;
	}

	@Override
	public void read() {
		//syncItems.add(new SyncItem());
		Properties p = new Properties();
		try {
			p.load(new FileInputStream("sync.properties"));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		String items = p.getProperty("items", "");
		for (String i: items.split(":")){
			syncItems.add(new SyncItem(p.getProperty(i + ".from"), p.getProperty(i + ".to")));
		}

	}

	@Override
	public List<SyncItem> getSyncItems() {
		return syncItems;
	}

}
