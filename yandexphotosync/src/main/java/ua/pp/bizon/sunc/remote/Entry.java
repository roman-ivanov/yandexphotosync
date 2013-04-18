package ua.pp.bizon.sunc.remote;



public interface Entry {

	String getParentUrl();

	String getPath();

	String getName();

	String getUrl();

    Entry getParent();

    void setParent(Entry e);

}
