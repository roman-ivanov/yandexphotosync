package ua.pp.bizon.sync.api.impl;

public class CachedSyncItem extends SyncItem {

	private Item from;
	private Item to;

	public CachedSyncItem(Item from, Item to) {
		super(from.getFullPath(), to.getFullPath());
		this.from = from;
		this.to = to;
	}

	public Item getFromItem() {
		return from;
	}

	public Item getToItem() {
		return to;
	}

}
