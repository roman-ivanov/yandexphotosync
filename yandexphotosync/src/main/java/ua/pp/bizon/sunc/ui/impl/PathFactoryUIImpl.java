package ua.pp.bizon.sunc.ui.impl;

import ua.pp.bizon.sunc.api.Path;
import ua.pp.bizon.sunc.api.PathFactory;
import ua.pp.bizon.sunc.api.impl.PathNotSupportedException;

public class PathFactoryUIImpl implements PathFactory {

    public PathFactoryUIImpl(PathFactory factory) {
        this.factory = factory;
    }

    private PathFactory factory;

    @Override
    public Path create(String string) throws PathNotSupportedException {
        return new UIPath(factory.create(string));
    }

}
