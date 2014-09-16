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

import org.junit.Ignore;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.FileNotFoundException;
import java.util.Date;

import fr.eo.api.manager.Manager;
import fr.eo.api.model.CharacterSheet;
import fr.eo.api.services.CharacterService;
import fr.eo.api.util.DateFormatTransformer;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * @author picon.software
 */
public class CharacterSheetTest extends AbstractTest {

	@Test
	public void serializer() {
		RegistryMatcher m = new RegistryMatcher();
		m.bind(Date.class, new DateFormatTransformer("yyyy-MM-dd HH:mm:ss"));

		Serializer serializer = new Persister(m);

		try {
			CharacterSheet eveApi = serializer.read(CharacterSheet.class,
					getResource("eveapi-char-sheet-test.xml"));

			assertThat(eveApi).isNotNull();
			assertThat(eveApi.getSkills()).isNotNull().isNotEmpty();

			assertThat(eveApi.getSkills()).containsKey(3431L);
			assertThat(eveApi.getSkills().get(3431L)).isEqualTo((short) 3);

			assertThat(eveApi.getSkills()).doesNotContainKey(3445L);

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Ignore
	@Test
	public void live() throws FileNotFoundException {

		CharacterService characterService = new Manager().characterService();

		TestCredential testCredential = getCredential();

		CharacterSheet characterSheet =
				characterService.characterSheet(testCredential.keyID,
						testCredential.vCode, testCredential.characterID);

		assertThat(characterSheet).isNotNull();
		assertThat(characterSheet.getSkills()).isNotNull().isNotEmpty();
	}
}
