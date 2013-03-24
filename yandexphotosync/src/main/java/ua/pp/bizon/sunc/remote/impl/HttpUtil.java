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

public class HttpUtil {
	
	private static Logger logger= LoggerFactory.getLogger(HttpUtil.class);

	public static InputStream getStream(String url) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		logger.trace("request: " + url);
		method.addRequestHeader("Authorization",  "OAuth " + OAuth.getToken());
		int response = client.executeMethod(method);
		logger.trace("response code: " + response);
		logger.trace("response stream: " + method.getResponseBodyAsString());
		return method.getResponseBodyAsStream();
	}

    public static InputStream postEntry(String url, String data) throws IOException {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        method.setRequestEntity(new StringRequestEntity(data, "application/atom+xml; charset=utf-8; type=entry", null));
        method.addRequestHeader("Authorization",  "OAuth " + OAuth.getToken());
        logger.trace("request url: " + url);
        logger.trace("reqest post data: " + data);
        int response = client.executeMethod(method);
        if (response > 201){
            logger.debug("request url: " + url);
            logger.debug("reqest post data: " + data);
            logger.debug("response status text : " + method.getStatusText());
            logger.debug("response headers : " + Arrays.asList(method.getRequestHeaders()));
            logger.debug("response code: " + response);
            logger.debug("response stream: " + method.getResponseBodyAsString());
                }
        logger.trace("response code: " + response);
        logger.trace("response stream: " + method.getResponseBodyAsString());
        return method.getResponseBodyAsStream();
    }

    public static InputStream postData(String url, String name, byte[] data, String id) throws HttpException, IOException {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        Part parts[] = new Part[4];
        parts[0] = new FilePart(name, new ByteArrayPartSource(name, data));
        parts[1] = new StringPart("album", id);
        parts[2] = new StringPart("title", name);
        parts[2] = new StringPart("storage_private", "true");
        HttpMethodParams params = new HttpMethodParams();
        method.setRequestEntity(new MultipartRequestEntity(parts, params));
        method.addRequestHeader("Authorization",  "OAuth " + OAuth.getToken());
        logger.trace("request url: " + url);
        logger.trace("reqest post data: " + method.getRequestEntity());
        int response = client.executeMethod(method);
        if (response > 201){
            logger.debug("request url: " + url);
            logger.debug("reqest post data: " + data);
            logger.debug("response status text : " + method.getStatusText());
            logger.debug("response headers : " + Arrays.asList(method.getRequestHeaders()));
            logger.debug("response code: " + response);
            logger.debug("response stream: " + method.getResponseBodyAsString());
                }
        logger.trace("response code: " + response);
        logger.trace("response stream: " + method.getResponseBodyAsString());
        return method.getResponseBodyAsStream();
    }

}
