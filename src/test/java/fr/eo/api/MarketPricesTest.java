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

import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import fr.eo.api.manager.Manager;
import fr.eo.api.model.market.prices.Prices;
import fr.eo.api.services.MarketService;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author picon.software
 */
public class MarketPricesTest extends AbstractTest {

	@Test
	public void fromJson() throws FileNotFoundException {
		Gson gson = new GsonBuilder().create();
		Prices prices = gson.fromJson(new InputStreamReader(getResource("crest-market-prices.json")),
				Prices.class);

		assertThat(prices).isNotNull();
	}

	@Ignore
	@Test
	public void live() {
		MarketService marketService = new Manager().marketService();

		Prices prices = marketService.prices();

		assertThat(prices).isNotNull();
		assertThat(prices.getItems()).isNotNull().isNotEmpty()
				.hasSize(prices.getTotalCount());
	}
}
