package ua.pp.bizon.sync;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import ua.pp.bizon.sync.api.IConfig;
import ua.pp.bizon.sync.api.ISyncThreadFactory;
import ua.pp.bizon.sync.api.impl.SyncItem;

@Controller
public class SyncApp implements Runnable {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
		new AnnotationConfigApplicationContext(SyncApp.class.getPackage()
				.getName()).getBean(SyncApp.class).start();
		LoggerFactory.getLogger(SyncApp.class).debug("app started");

	}

	@Autowired
	private IConfig config;

	@Autowired
	private ISyncThreadFactory factory;

	@Autowired
	private ApplicationContext context;

	private ExecutorService service;

	public void start() {
		config.read();
		service = Executors.newFixedThreadPool(config.getThreadCount());
		service.execute(this);

	}

	@Override
	public void run() {
		addToQueue(config.getSyncItems());
		service.execute(new SyncAppShutdowner(service));
	}

	public void addToQueue(List<SyncItem> childs) {
		for (SyncItem i : childs) {
			service.execute(factory.createSyncThread(i, this));
		}

	}

}
