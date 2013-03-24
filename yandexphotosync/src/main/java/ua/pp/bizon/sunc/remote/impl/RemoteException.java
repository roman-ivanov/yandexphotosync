package ua.pp.bizon.sunc.remote.impl;

public class RemoteException extends Exception {

	private static final long serialVersionUID = 263880645326537403L;

	public RemoteException() {
		super();
	}

	public RemoteException(String message, Throwable cause) {
		super(message, cause);
	}

	public RemoteException(String message) {
		super(message);
	}

	public RemoteException(Throwable cause) {
		super(cause);
	}

}
