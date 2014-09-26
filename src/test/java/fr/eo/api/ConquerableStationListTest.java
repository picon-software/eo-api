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
import fr.eo.api.model.ConquerableStationList;
import fr.eo.api.services.EveService;
import fr.eo.api.util.DateFormatTransformer;
import org.junit.Ignore;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * @author picon.software
 */
public class ConquerableStationListTest extends AbstractTest {

    @Test
    public void serializer() {
        RegistryMatcher m = new RegistryMatcher();
        m.bind(Date.class, new DateFormatTransformer("yyyy-MM-dd HH:mm:ss"));

        Serializer serializer = new Persister(m);

        try {
            ConquerableStationList eveApi = serializer.read(ConquerableStationList.class,
                    getResource("eveapi-conquerable-station-test.xml"));

            assertThat(eveApi).isNotNull();
            assertThat(eveApi.getStations()).isNotNull().isNotEmpty().hasSize(829);

            assertThat(eveApi.getStations().get(0).stationID).isEqualTo(61000527);
            assertThat(eveApi.getStations().get(0).stationName)
                    .isEqualTo("KV-8SN VIII - Dont Refine Here Idiot");
            assertThat(eveApi.getStations().get(0).stationTypeID)
                    .isEqualTo(21645);
            assertThat(eveApi.getStations().get(0).solarSystemID)
                    .isEqualTo(30004348);

            for (ConquerableStationList.Station station : eveApi.getStations()) {
                assertThat(station.stationID).isGreaterThan(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Ignore
    @Test
    public void live() {
        EveService eveService = new Manager().eveService();

        ConquerableStationList stationList = eveService.conquerableStationList();

        assertThat(stationList).isNotNull();
        assertThat(stationList.getStations()).isNotNull().isNotEmpty();
    }
}
