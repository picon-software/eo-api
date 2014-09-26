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

import fr.eo.api.manager.Manager;
import fr.eo.api.model.ApiKeyInfo;
import fr.eo.api.services.AccountService;
import fr.eo.api.util.DateFormatTransformer;
import org.junit.Ignore;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author picon.software
 */
public class ApiKeyInfoTest extends AbstractTest {

    @Test
    public void serializer() {
        RegistryMatcher m = new RegistryMatcher();
        m.bind(Date.class, new DateFormatTransformer("yyyy-MM-dd HH:mm:ss"));

        Serializer serializer = new Persister(m);
        try {
            InputStream inputStream =
                    getResource("eveapi-api-key-info-test.xml");

            assertNotNull("no input stream !", inputStream);

            ApiKeyInfo eveApi = serializer.read(ApiKeyInfo.class, inputStream);

            assertEquals("Character", eveApi.result.key.type);
            assertEquals(59760264, eveApi.result.key.accessMask);

            assertThat(eveApi.result.key.rowset).isNotNull().isNotEmpty();

            assertEquals(898901870, eveApi.result.key.rowset.get(0).characterID);
            assertEquals("Desmont McCallock", eveApi.result.key.rowset.get(0).characterName);
            assertEquals(1000009, eveApi.result.key.rowset.get(0).corporationID);
            assertEquals("Caldari Provisions", eveApi.result.key.rowset.get(0).corporationName);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Ignore
    @Test
    public void live() throws FileNotFoundException {
        AccountService accountService = new Manager().accountService();

        TestCredential testCredential = getCredential();

        accountService.apiKeyInfo(testCredential.keyID, testCredential.vCode);
    }
}
