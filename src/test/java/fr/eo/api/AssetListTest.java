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
import fr.eo.api.model.AssetList;
import fr.eo.api.services.CharacterService;
import fr.eo.api.util.DateFormatTransformer;
import org.junit.Ignore;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.FileNotFoundException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * @author picon.software
 */
public class AssetListTest extends AbstractTest {

    @Test
    public void serializer() {
        RegistryMatcher m = new RegistryMatcher();
        m.bind(Date.class, new DateFormatTransformer("yyyy-MM-dd HH:mm:ss"));

        Serializer serializer = new Persister(m);

        try {
            AssetList eveApi = serializer.read(AssetList.class,
                    getResource("eveapi-char-asset-list-test.xml"));

            assertThat(eveApi).isNotNull();
            assertThat(eveApi.getAssets()).isNotNull().isNotEmpty().containsKey(60011314L);

            assertThat(eveApi.getAssets().get(60011314L)).containsKey(12822L);
            assertThat(eveApi.getAssets().get(60011314L).get(12822L)).isEqualTo(6L);

            assertThat(eveApi.getAssets().get(60011314L)).containsKey(3025L);
            assertThat(eveApi.getAssets().get(60011314L).get(3025L)).isEqualTo(6L);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Ignore
    @Test
    public void live() throws FileNotFoundException {
        CharacterService characterService = new Manager().characterService();

        TestCredential testCredential = getCredential();

        AssetList assetList = characterService.assetList(testCredential.keyID,
                testCredential.vCode, testCredential.characterID);

        assertThat(assetList).isNotNull();
        assertThat(assetList.getAssets()).isNotNull().isNotEmpty();
    }
}
