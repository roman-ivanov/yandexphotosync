package ua.pp.bizon.sunc.remote.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.httpclient.HttpException;
import org.junit.Test;

import ua.pp.bizon.sunc.remote.api.OAuth;
import ua.pp.bizon.sunc.remote.api.OAuthUI;

public class OAuthTest {

	@Test
	public void test() throws HttpException, IOException {
	    final AtomicInteger integer = new AtomicInteger(0);
		OAuthImpl oAuthImpl = new OAuthImpl();
		oAuthImpl.setAuthUI(new OAuthUI() {
            
            @Override
            public void login(OAuth oAuth) {
                integer.incrementAndGet();
                oAuth.setToken("token");
            }
        });
        assertNotNull(oAuthImpl.getToken());
        assertEquals("token", oAuthImpl.getToken());
        assertEquals(integer.get(), 1);

		System.out.println(Arrays.asList("1/2/3/4".split("/", 2)));
        System.out.println(Arrays.asList("1/2/3/4".split(":", 2)));

	}

}
