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

package fr.eo.evecentral;

import org.junit.Ignore;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;

import fr.eo.api.AbstractTest;
import fr.eo.evecentral.model.MarketStat;
import fr.eo.evecentral.service.EveCentralAPI;
import retrofit.RestAdapter;
import retrofit.converter.SimpleXMLConverter;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author picon.software
 */
public class MarketStatTest extends AbstractTest {

	@Test
	public void serializer() throws Exception {
		Serializer serializer = new Persister();

		try {
			MarketStat marketStat = serializer.read(MarketStat.class,
					getResource("eve-central-test.xml"));

			assertThat(marketStat).isNotNull();
			assertThat(marketStat.types).isNotEmpty();
			assertThat(marketStat.getTypeById(34)).isNotNull();
			assertThat(marketStat.getTypeById(34).buy).isNotNull();
			assertThat(marketStat.getTypeById(34).buy.volume).isEqualTo(29639236008L);

		} catch (Exception e) {
			//fail(e.getMessage());
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void live() throws IOException {
		long time = System.currentTimeMillis();
		// Create an HTTP client that uses a cache on the file system. Android applications should use
		// their Context to get a cache directory.
//		OkHttpClient okHttpClient = new OkHttpClient();
//		File cacheDir = new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
//		HttpResponseCache cache = new HttpResponseCache(cacheDir, 1024);
//		okHttpClient.setResponseCache(cache);

		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(EveCentralAPI.URL)
//				.setClient(new OkClient(okHttpClient))
				.setConverter(new SimpleXMLConverter())
				.setLogLevel(RestAdapter.LogLevel.HEADERS)
				.build();

		EveCentralAPI eveCentralAPI = restAdapter.create(EveCentralAPI.class);
		System.out.println("Initialisation en " + (System.currentTimeMillis() - time) / 1000f + "s");

		time = System.currentTimeMillis();
		eveCentralAPI.marketstat(null, 30000142, 34);
		System.out.println("Execution en " + (System.currentTimeMillis() - time) / 1000f + "s\n");

		for (int i = 0; i < 4; i++) {
			time = System.currentTimeMillis();
			eveCentralAPI.marketstat(null, 30000142, 34);
			System.out.println("Execution en " + (System.currentTimeMillis() - time) / 1000f + "s\n");

//			assertThat(cache.getHitCount()).isGreaterThan(0);

		}
	}
}
