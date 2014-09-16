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

import org.fest.assertions.api.Assertions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class AbstractTest {

	private static final String RESOURCES_RELATIVE_FOLDER = "eo-api/src/test/resources/";

	protected static InputStream getResource(String fileName) throws FileNotFoundException {
		File resource = new File(RESOURCES_RELATIVE_FOLDER + fileName);
		return new FileInputStream(resource);
	}

	protected TestCredential getCredential() throws FileNotFoundException {
		TestCredential testCredential;
		try {
			testCredential = TestCredential.load();
		} catch (FileNotFoundException e) {
			Assertions.fail("You need to create a credential.json file in the resources folder. Read the javadoc for more info.");
			throw e;
		}

		return testCredential;
	}

	/**
	 * The test credential representation
	 *
	 * <br /><pre>
	 * {
	 * "keyID": &lt;your api keyID&gt;,
	 * "vCode": "&lt;your api vCode&gt;",
	 * "characterID": &lt;a character id&gt;
	 * }
	 * </pre>
	 */
	public static class TestCredential {
		public int keyID;
		public String vCode;
		public int characterID;

		public static TestCredential load() throws FileNotFoundException {
			Gson gson = new GsonBuilder().create();

			return gson.fromJson(new InputStreamReader(getResource("credential.json")),
					TestCredential.class);
		}
	}
}
