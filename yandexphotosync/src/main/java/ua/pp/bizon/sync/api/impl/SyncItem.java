package ua.pp.bizon.sync.api.impl;

public class SyncItem {
	
	private String from;
	private String to;
	

	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}


	public SyncItem(String from, String to) {
		super();
		
		this.from = from;
		this.to = to;
	}


	@Override
	public String toString() {
		return "SyncItem [from=" + from + ", to=" + to + "]";
	}

	
}
