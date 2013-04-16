package ua.pp.bizon.sunc.ui;

import java.util.Collections;
import java.util.EventListener;
import java.util.List;

import javax.swing.event.EventListenerList;

import ua.pp.bizon.sunc.api.Path;

public class FileFrameBean {
    
    private Path path;
    private String[] roots = new String[0];
    private List<Path> elements = Collections.emptyList();
    private EventListenerList eventListenerList = new EventListenerList();
    
    static interface PathUpdatedEvent extends EventListener {
        void update(Path newPath);
    }
    
    public void addPathUpdatedEvent(PathUpdatedEvent e){
        eventListenerList.add(PathUpdatedEvent.class, e);
    }
    
    public void removePathUpdatedEvent(PathUpdatedEvent e){
        eventListenerList.remove(PathUpdatedEvent.class, e);
    } 

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
        for (PathUpdatedEvent e: eventListenerList.getListeners(PathUpdatedEvent.class)){
            e.update(path);
        }
    }

    public String[] getRoots() {
        return roots;
    }

    public void setRoots(String[] roots) {
        this.roots = roots;
    }

    public List<Path> getElements() {
        return elements;
    }

    public void setElements(List<Path> elements) {
        this.elements = elements;
    }

}
