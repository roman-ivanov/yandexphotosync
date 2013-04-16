package ua.pp.bizon.sunc.remote.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ua.pp.bizon.sunc.remote.api.HttpUtil;
import ua.pp.bizon.sunc.remote.api.OAuth;

public class HttpUtilImpl implements HttpUtil {
    
    @Autowired
    private OAuth auth;
    
    /* (non-Javadoc)
     * @see ua.pp.bizon.sunc.remote.impl.HttpUtil#setAuth(ua.pp.bizon.sunc.remote.api.OAuth)
     */
    @Override
    public void setAuth(OAuth auth) {
        this.auth = auth;
    }
    
    /* (non-Javadoc)
     * @see ua.pp.bizon.sunc.remote.impl.HttpUtil#getAuth()
     */
    @Override
    public OAuth getAuth() {
        return auth;
    }
	
	private static Logger logger= LoggerFactory.getLogger(HttpUtilImpl.class);

	/* (non-Javadoc)
     * @see ua.pp.bizon.sunc.remote.impl.HttpUtil#getStream(java.lang.String)
     */
	@Override
    public InputStream getStream(String url) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		logger.trace("getStream: url=" + url);
		method.addRequestHeader("Authorization",  "OAuth " + auth.getToken());
		int response = client.executeMethod(method);
		logger.trace("response code: " + response);
		logger.trace("response stream: " + method.getResponseBodyAsString());
		return method.getResponseBodyAsStream();
	}

    /* (non-Javadoc)
     * @see ua.pp.bizon.sunc.remote.impl.HttpUtil#postEntry(java.lang.String, java.lang.String)
     */
    @Override
    public  InputStream postEntry(String url, String data) throws IOException {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        method.setRequestEntity(new StringRequestEntity(data, "application/atom+xml; charset=utf-8; type=entry", null));
        method.addRequestHeader("Authorization",  "OAuth " + auth.getToken());
        logger.trace("request url: " + url);
        logger.trace("reqest post data: " + data);
        int response = client.executeMethod(method);
        if (response > 201){
            logger.warn("postEntry: url=" + url);
            logger.warn("reqest post data: " + data);
            logger.warn("response status text : " + method.getStatusText());
            logger.warn("response headers : " + Arrays.asList(method.getRequestHeaders()));
            logger.warn("response code: " + response);
            logger.warn("response stream: " + method.getResponseBodyAsString());
                }
        logger.trace("response code: " + response);
        logger.trace("response stream: " + method.getResponseBodyAsString());
        return method.getResponseBodyAsStream();
    }

    /* (non-Javadoc)
     * @see ua.pp.bizon.sunc.remote.impl.HttpUtil#postData(java.lang.String, java.lang.String, byte[], java.lang.String)
     */
    @Override
    public InputStream postData(String url, String name, byte[] data, String albumId) throws HttpException, IOException {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        Part parts[] = new Part[4];
        parts[0] = new FilePart("image", new ByteArrayPartSource(name, data), "image/jpeg", null);
        parts[1] = new StringPart("album", albumId);
        parts[2] = new StringPart("title", name);
        parts[3] = new StringPart("storage_private", "true");
        HttpMethodParams params = new HttpMethodParams();
        method.setRequestEntity(new MultipartRequestEntity(parts, params));
        method.addRequestHeader("Authorization",  "OAuth " + auth.getToken());
        logger.trace("post data: url=" + url);
        logger.trace("reqest post data: " + method.getRequestEntity());
        int response = client.executeMethod(method);
        if (response > 201){
            logger.warn("request url: " + url);
            logger.warn("reqest post data length: " + data.length);
            logger.warn("response status text : " + method.getStatusText());
            logger.warn("response headers : " + Arrays.asList(method.getRequestHeaders()));
            logger.warn("response code: " + response);
            logger.warn("response stream: " + method.getResponseBodyAsString());
                }
        logger.trace("response code: " + response);
        logger.trace("response stream: " + method.getResponseBodyAsString());
        return method.getResponseBodyAsStream();
    }

    public String postRawEntity(String url, String postData, String encode) throws IOException, RemoteException {
        logger.trace("postRawEntity: url=" + url);
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        method.setRequestEntity(new StringRequestEntity(postData, encode, null));
        logger.trace("reqest post data: " + method.getRequestEntity());
        int response = client.executeMethod(method);
        if (response > 201){
            logger.warn("request url: " + url);
            logger.warn("response status text : " + method.getStatusText());
            logger.warn("response headers : " + Arrays.asList(method.getRequestHeaders()));
            logger.warn("response code: " + response);
            logger.warn("response stream: " + method.getResponseBodyAsString());
            throw new RemoteException(method.getStatusText() + "\n\n" + method.getResponseBodyAsString());
        }
        logger.trace("response code: " + response);
        logger.trace("response stream: " + method.getResponseBodyAsString());
        return method.getResponseBodyAsString();
    }

}
