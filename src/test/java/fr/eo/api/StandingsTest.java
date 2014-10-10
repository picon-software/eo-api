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
import fr.eo.api.model.Standings;
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
import static org.assertj.core.api.Assertions.entry;
import static org.junit.Assert.fail;

/**
 * @author picon.software
 */
public class StandingsTest extends AbstractTest {

    @Test
    public void serializer() {
        RegistryMatcher m = new RegistryMatcher();
        m.bind(Date.class, new DateFormatTransformer("yyyy-MM-dd HH:mm:ss"));

        Serializer serializer = new Persister(m);

        try {
            Standings eveApi = serializer.read(Standings.class,
                    getResource("eveapi-char-standing-test.xml"));

            assertThat(eveApi).isNotNull();
            assertThat(eveApi.getStandings()).isNotNull().isNotEmpty().contains(entry(3009841L, 0.1f));
            assertThat(eveApi.getStandings()).isNotNull().isNotEmpty().contains(entry(3009846L, 0.19f));
            assertThat(eveApi.getStandings()).isNotNull().isNotEmpty().contains(entry(1000061L, 0f));
            assertThat(eveApi.getStandings()).isNotNull().isNotEmpty().contains(entry(1000064L, 0.34f));
            assertThat(eveApi.getStandings()).isNotNull().isNotEmpty().contains(entry(1000094L, 0.02f));
            assertThat(eveApi.getStandings()).isNotNull().isNotEmpty().contains(entry(500003L, -0.1f));
            assertThat(eveApi.getStandings()).isNotNull().isNotEmpty().contains(entry(500020L, -1f));

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

        Standings standings = characterService.standings(testCredential.keyID,
                testCredential.vCode, testCredential.characterID);

        assertThat(standings).isNotNull();
        assertThat(standings.getStandings()).isNotNull().isNotEmpty();
    }
}
