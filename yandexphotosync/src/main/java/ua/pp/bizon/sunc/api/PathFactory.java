package ua.pp.bizon.sunc.api;

import ua.pp.bizon.sunc.api.impl.PathNotSupportedException;

public interface PathFactory {

    Path create(String string) throws PathNotSupportedException;

}
