package ua.pp.bizon.sunc.remote.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.httpclient.HttpException;
import org.junit.Test;

public class OAuthTest {

	@Test
	public void test() throws HttpException, IOException {
		assertNotNull(new OAuthImpl().getToken());
		System.out.println(Arrays.asList("1/2/3/4".split("/", 2)));
        System.out.println(Arrays.asList("1/2/3/4".split(":", 2)));

	}

}
