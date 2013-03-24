package ua.pp.bizon.sunc.remote;



public interface Entry {

	String getParentUrl();

	void setParent(Entry entry);

	String getPath();

	String getName();

	String getUrl();

    Entry getParent();

}
