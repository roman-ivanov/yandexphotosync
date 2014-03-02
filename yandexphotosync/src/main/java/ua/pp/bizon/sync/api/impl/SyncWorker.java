package ua.pp.bizon.sync.api.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.LoggerFactory;

import ua.pp.bizon.sunc.ui.Direction;
import ua.pp.bizon.sync.SyncApp;
import ua.pp.bizon.sync.api.ISyncItemController;

public class SyncWorker implements Runnable {

	private final SyncItem item;
	private ISyncItemController controller;
	private SyncApp app;

	public SyncWorker(SyncItem i, SyncApp service,
			ISyncItemController controller) {
		this.item = i;
		this.app = service;
		this.controller = controller;
	}

	@Override
	public void run() {
		if (controller.isDirectory(item)) {
			controller.createDirectory(item);
			app.addToQueue(controller.getChilds(item));
		} else {
			try {
				sync();
			} catch (IOException e) {
				LoggerFactory.getLogger(getClass()).error(
						"error diring sync item " + item + ", detail: "
								+ e.getMessage(), e);
			}
		}

	}

	protected void sync() throws IOException {
		LoggerFactory.getLogger(getClass()).debug("sync:" + item);

		OutputStream output = controller.getOutputStream(item, Direction.TO);
		InputStream input = controller.getInputStream(item, Direction.FROM);
		byte b[] = new byte[1024];
		int readCount = 0;
		try {
			while ((readCount = input.read(b)) > 0) {
				if (output != null)
					output.write(b, 0, readCount);
			}
		} finally {
			if (output != null)
				output.close();
			if (input != null)
				input.close();
		}

	}
}
