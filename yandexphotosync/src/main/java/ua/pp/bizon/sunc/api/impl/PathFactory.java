package ua.pp.bizon.sunc.api.impl;

import ua.pp.bizon.sunc.api.Path;

public class PathFactory {

    public static Path create(String string) throws PathNotSupportedException {
        if (string.startsWith("yandex:"))
            return new YandexPath(string.substring(7));
        else if (string.startsWith("file:"))
            return new LocalPath(string.substring(5));
        else 
            throw new PathNotSupportedException();
    }

}
