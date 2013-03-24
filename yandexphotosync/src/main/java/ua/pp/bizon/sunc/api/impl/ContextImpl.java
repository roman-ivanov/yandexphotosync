package ua.pp.bizon.sunc.api.impl;

import ua.pp.bizon.sunc.api.Context;
import ua.pp.bizon.sunc.remote.impl.AuthToken;


public class ContextImpl implements Context {

    @Override
    public AuthToken getAuthToken() {
        return new AuthToken();
    }

}
