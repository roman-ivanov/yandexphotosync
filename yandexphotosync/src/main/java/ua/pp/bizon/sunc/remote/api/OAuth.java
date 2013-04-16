package ua.pp.bizon.sunc.remote.api;

public interface OAuth {

    public static final String CLIENT_SECRET = "oauth.client.secret";
    public static final String CLIENT_ID = "oauth.client.id";

    public abstract String getToken();

    public abstract void setToken(String token);

    public abstract void setAuthUI(OAuthUI authUI);

}
