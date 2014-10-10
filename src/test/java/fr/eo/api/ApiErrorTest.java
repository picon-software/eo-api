/*
 * Copyright (C) 2014 Picon software
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package fr.eo.api;

import com.squareup.okhttp.OkHttpClient;
import fr.eo.api.error.NetworkErrorHandler;
import fr.eo.api.manager.Manager;
import fr.eo.api.model.ApiKeyInfo;
import fr.eo.api.services.AccountService;
import fr.eo.api.util.DateFormatTransformer;
import org.junit.Ignore;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;
import retrofit.RetrofitError;
import retrofit.client.OkClient;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author picon.software
 */
public class ApiErrorTest extends AbstractTest {

    @Test
    public void serializer() {
        RegistryMatcher m = new RegistryMatcher();
        m.bind(Date.class, new DateFormatTransformer("yyyy-MM-dd HH:mm:ss"));

        Serializer serializer = new Persister(m);

        try {
            InputStream inputStream =
                    getResource("eveapi-error-test.xml");

            assertNotNull("no input stream !", inputStream);

            ApiKeyInfo eveApi = serializer.read(ApiKeyInfo.class, inputStream);

            assertThat(eveApi).isNotNull();
            assertThat(eveApi.error).isNotNull();
            assertThat(eveApi.error.label).isNotNull().isEqualTo("Cache is invalid");
            assertThat(eveApi.error.code).isEqualTo(1001);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Ignore
    @Test(expected = RetrofitError.class)
    public void testTimeout() throws FileNotFoundException {

        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(1, TimeUnit.MILLISECONDS);
        client.setReadTimeout(1, TimeUnit.MILLISECONDS);

        AccountService accountService = new Manager()
                .setClient(new OkClient(client))
                .setErrorHandler(new NetworkErrorHandler() {
                    @Override
                    public void onNoInternetError(RetrofitError cause) {
                        System.err.println("No internet connection !");
                    }

                    @Override
                    public void onTimeOutError(RetrofitError cause) {
                        assertTrue(true);
                    }
                }).accountService();

        TestCredential testCredential = getCredential();

        accountService.apiKeyInfo(testCredential.keyID, testCredential.vCode);

        fail("No error occured !");
    }

    @Ignore
    @Test(expected = RetrofitError.class)
    public void testNoInternet() throws FileNotFoundException {

        OkHttpClient client = new OkHttpClient();
        client.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("toto", 1234)));

        AccountService accountService = new Manager()
                .setClient(new OkClient(client))
                .setErrorHandler(new NetworkErrorHandler() {
                    @Override
                    public void onNoInternetError(RetrofitError cause) {
                        assertTrue(true);
                    }

                    @Override
                    public void onTimeOutError(RetrofitError cause) {
                        fail("No Time out here !");
                    }
                }).accountService();

        TestCredential testCredential = getCredential();

        accountService.apiKeyInfo(testCredential.keyID, testCredential.vCode);

        fail("No error occured !");
    }
}
