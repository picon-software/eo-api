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
import fr.eo.api.model.Jobs;
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
public class IndustryJobsTest extends AbstractTest {

    @Test
    public void serializer() {
        RegistryMatcher m = new RegistryMatcher();
        m.bind(Date.class, new DateFormatTransformer("yyyy-MM-dd HH:mm:ss"));

        Serializer serializer = new Persister(m);

        try {
            Jobs eveApi = serializer.read(Jobs.class,
                    getResource("eveapi-char-industry-jobs.xml"));

            assertThat(eveApi).isNotNull();
            assertThat(eveApi.getJobs()).isNotNull().isNotEmpty()
                    .extracting("facilityID").contains(60006382L, 1015338129652L, 1015338129650L);
            assertThat(eveApi.getJobs())
                    .extracting("stationID").contains(60006382L, 1015338119317L);
            assertThat(eveApi.getJobs())
                    .extracting("solarSystemID").contains(30005194L, 30005195L);
            assertThat(eveApi.getJobs())
                    .extracting("activityID").contains(1L, 3L, 4L, 5L, 7L, 8L);
            assertThat(eveApi.getJobs())
                    .extracting("blueprintTypeID").contains(2047L, 25862L, 785L, 10040L, 30614L, 1137L);
            assertThat(eveApi.getJobs().get(0).endDate).isNotNull();

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
