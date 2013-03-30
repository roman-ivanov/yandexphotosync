package ua.pp.bizon.sunc.remote.impl;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ua.pp.bizon.sunc.remote.Entry;

public abstract class AbstractEntry implements Entry {

    final protected Node element;
    final protected ServiceDocument root;
    private Entry parent;
    private String parentUrl;
    private String selfUrl;
    private String title;
    private String id;

    public AbstractEntry(Node item, ServiceDocument root) {
        this.element = item;
        this.root = root;
    }

    protected String getLink(String string) {
        NodeList list = element.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals("link")) {
                if ((list.item(i).getAttributes().getNamedItem("rel").getNodeValue().equals(string))) {
                    return list.item(i).getAttributes().getNamedItem("href").getNodeValue();
                }
            }
        }
        return null;
    }

    public String getParentUrl() {
        if (parentUrl == null) {
            parentUrl = getLink("album");
        }
        return parentUrl;
    }

    @Override
    public String getUrl() {
        if (selfUrl == null)
            selfUrl = getLink("self");
        return selfUrl;
    }

    public String getId() {
        if (id == null) {
            NodeList list = element.getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                if (list.item(i).getNodeName().equals("id")) {
                    id = list.item(i).getTextContent();
                    break;
                }
            }
        }
        return id;
    }

    @Override
    public void setParent(Entry entry) {
        this.parent = entry;
    }

    @Override
    public Entry getParent() {
        return parent;
    }

    @Override
    public String getPath() {
        return (parent == null ? "yandex:" : parent.getPath()) + "/" + getName();
    }

    @Override
    public String getName() {
        if (title == null)
            title = getTitle();
        return title;
    }

    protected String getTitle() {
        NodeList list = element.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals("title")) {
                return list.item(i).getTextContent();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "parentUrl= " + getParentUrl() + ", url=" + getUrl() + ", path=" + getPath();
    }

}
