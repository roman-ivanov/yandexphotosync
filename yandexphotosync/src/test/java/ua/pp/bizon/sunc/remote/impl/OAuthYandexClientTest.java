package ua.pp.bizon.sunc.remote.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeansException;

import ua.pp.bizon.sunc.api.impl.StoreImplTest;

public class OAuthYandexClientTest {

    private OAuthYandexClient oAuthYandexClient;

    @Before
    public void before() {
        oAuthYandexClient = new OAuthYandexClient();
        oAuthYandexClient.util = new HttpUtilImpl() {
            @Override
            public String postRawEntity(String url, String postData, String encode) throws IOException {
                if (count++ == 0)
                    return "{\"access_token\": \"e848dca1f8d746859eec\", \"token_type\": \"bearer\"}";
                else if (count++ == 1)
                    return "{\"error\" : \"error\"}";
                else
                    return "<!DOCTYPE html>\r\n\r\n<html>\r\n" + "<head>\r\n" + "  <title>Серверная ошибка</title>\r\n"
                            + "</head>\r\n" + "\r\n" + "<body>\r\n" + "    <p>Неизвестная серверная ошибка</p>\r\n"
                            + "</body>\r\n" + "</html>\r\n";
            }

            private int count = 0;
        };
        oAuthYandexClient.store = StoreImplTest.createTempStore();
    }

    @Test
    public void testGetOAuthKeyString() throws BeansException, IOException, RemoteException {
        assertEquals("e848dca1f8d746859eec", oAuthYandexClient.getOAuthKey("1670890"));
    }

    @Test(expected = RemoteException.class)
    public void testGetOAuthKeyString1() throws BeansException, IOException, RemoteException {
        assertEquals("e848dca1f8d746859eec", oAuthYandexClient.getOAuthKey("1670890"));
        oAuthYandexClient.getOAuthKey("1670890");
        fail("you shouldn't be here");
    }
    
    

    @Test(expected = RemoteException.class)
    public void testGetOAuthKeyString2() throws BeansException, IOException, RemoteException {
        assertEquals("e848dca1f8d746859eec", oAuthYandexClient.getOAuthKey("1670890"));
        try {
        oAuthYandexClient.getOAuthKey("1670890");
        } catch(RemoteException e){}
        oAuthYandexClient.getOAuthKey("1670890");
        fail("you shouldn't be here");
    }

}
