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

package fr.eo.api.manager;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.util.Date;

import fr.eo.api.Constants;
import fr.eo.api.error.DefaultErrorHandler;
import fr.eo.api.error.NetworkErrorHandler;
import fr.eo.api.services.AccountService;
import fr.eo.api.services.CharacterService;
import fr.eo.api.services.EveService;
import fr.eo.api.services.IndustryService;
import fr.eo.api.services.MarketService;
import fr.eo.api.util.DateFormatTransformer;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.converter.SimpleXMLConverter;

public class Manager {

	/**
	 * Whether to return more detailed log output.
	 */
	private boolean mIsDebug;

	private Client mClient;

	private NetworkErrorHandler mErrorHandler;

	/**
	 * Currently valid instance of RestAdapter for eve online api services.
	 */
	private RestAdapter mEveOnlineRestAdapter;

	/**
	 * Currently valid instance of RestAdapter for crest api services.
	 */
	private RestAdapter mCrestRestAdapter;

	/**
	 * Create a new manager instance.
	 */
	public Manager() {
	}

	public Manager setIsDebug(boolean isDebug) {
		mIsDebug = isDebug;
		mEveOnlineRestAdapter = null;
		mCrestRestAdapter = null;
		return this;
	}

	public Manager setClient(Client mClient) {
		this.mClient = mClient;
		mEveOnlineRestAdapter = null;
		mCrestRestAdapter = null;
		return this;
	}

	public Manager setErrorHandler(NetworkErrorHandler mErrorHandler) {
		this.mErrorHandler = mErrorHandler;
		mEveOnlineRestAdapter = null;
		mCrestRestAdapter = null;
		return this;
	}

	/**
	 * If no instance exists yet, builds a new {@link RestAdapter} using the currently set API key
	 * and debug flag.
	 */
	private RestAdapter buildEveOnlineRestAdapter() {
		if (mEveOnlineRestAdapter == null) {
			RegistryMatcher m = new RegistryMatcher();
			m.bind(Date.class, new DateFormatTransformer("yyyy-MM-dd HH:mm:ss"));

			Serializer serializer = new Persister(m);

			RestAdapter.Builder builder = new RestAdapter.Builder()
					.setEndpoint(Constants.EVE_ONLINE_API_ENDPOINT_URL)
					.setConverter(new SimpleXMLConverter(serializer));

			if (mIsDebug) {
				builder.setLogLevel(RestAdapter.LogLevel.HEADERS);
			}

			if (mClient != null) {
				builder.setClient(mClient);
			}

			if (mErrorHandler != null) {
				builder.setErrorHandler(new DefaultErrorHandler(mErrorHandler));
			}

			mEveOnlineRestAdapter = builder.build();
		}

		return mEveOnlineRestAdapter;
	}

	/**
	 * If no instance exists yet, builds a new {@link RestAdapter} using the currently set API key
	 * and debug flag.
	 */
	private RestAdapter buildCrestRestAdapter() {
		if (mCrestRestAdapter == null) {
			RestAdapter.Builder builder = new RestAdapter.Builder()
					.setEndpoint(Constants.CREST_API_ENDPOINT_URL);

			if (mIsDebug) {
				builder.setLogLevel(RestAdapter.LogLevel.FULL);
			}

			mCrestRestAdapter = builder.build();
		}

		return mCrestRestAdapter;
	}

	public AccountService accountService() {
		return buildEveOnlineRestAdapter().create(AccountService.class);
	}

	public CharacterService characterService() {
		return buildEveOnlineRestAdapter().create(CharacterService.class);
	}

	public EveService eveService() {
		return buildEveOnlineRestAdapter().create(EveService.class);
	}

	public IndustryService industryService() {
		return buildCrestRestAdapter().create(IndustryService.class);
	}

	public MarketService marketService() {
		return buildCrestRestAdapter().create(MarketService.class);
	}
}
