package ua.pp.bizon.sunc.api;

import ua.pp.bizon.sunc.remote.impl.AuthToken;

public interface Context {

    AuthToken getAuthToken();

}
