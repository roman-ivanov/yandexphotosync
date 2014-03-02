package ua.pp.bizon.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.LoggerFactory;

public class SyncAppShutdowner implements Runnable {

	private ExecutorService service;
	public SyncAppShutdowner(ExecutorService service) {
	this.service = service;
	}

	@Override
	public void run() {
		LoggerFactory.getLogger(getClass()).debug("try to shutdown");
		if (((ThreadPoolExecutor)service).getQueue().isEmpty() && ((ThreadPoolExecutor)service).getActiveCount() <= 1){
			LoggerFactory.getLogger(getClass()).debug("shutdown!");
			service.shutdown();
		} else {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			service.execute(this);
		}
	}

}
