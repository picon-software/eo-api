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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.eo.api.manager.Manager;
import fr.eo.api.model.industry.facilities.Facilities;
import fr.eo.api.services.IndustryService;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author picon.software
 */
public class IndustryFacilitiesTest extends AbstractTest {

    @Test
    public void fromJson() throws FileNotFoundException {
        Gson gson = new GsonBuilder().create();
        Facilities facilities = gson.fromJson(new InputStreamReader(getResource("crest-industry-facilities.json")),
                Facilities.class);

        assertThat(facilities).isNotNull();
    }

    @Ignore
    @Test
    public void live() {
        IndustryService industryService = new Manager().industryService();

        Facilities facilities = industryService.facilities();

        assertThat(facilities).isNotNull();
        assertThat(facilities.getItems()).isNotNull().isNotEmpty()
                .hasSize(facilities.getTotalCount());
    }
}
