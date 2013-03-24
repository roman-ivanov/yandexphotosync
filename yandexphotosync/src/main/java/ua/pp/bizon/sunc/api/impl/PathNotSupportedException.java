package ua.pp.bizon.sunc.api.impl;

public class PathNotSupportedException extends Exception {

    private static final long serialVersionUID = 6416067608493253575L;

    public PathNotSupportedException() {
    }

    public PathNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PathNotSupportedException(String message) {
        super(message);
    }

    public PathNotSupportedException(Throwable cause) {
        super(cause);
    }

}
