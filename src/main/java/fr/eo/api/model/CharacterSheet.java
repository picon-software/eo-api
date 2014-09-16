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

package fr.eo.api.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author picon.software
 */
@Root(strict = false)
public class CharacterSheet extends ApiResult {

	private static final long serialVersionUID = -3100515266562778698L;

	@Element(required = false)
	public Result result;

	private Map<Long, Short> skills;
	public long characterID;

	public CharacterSheet() {
	}

	@Root(strict = false)
	public static final class Result {
		@Element
		public long characterID;
		@Element
		public String name;
		@Element
		public String corporationName;
		@Element
		public long corporationID;
		@Element(required = false)
		public String allianceName;
		@Element(required = false)
		public long allianceID;
		@ElementList(inline = true, entry = "rowset")
		public List<Rowset> rowsets;
	}

	@Root(strict = false)
	public static final class Rowset {
		@Attribute
		public String name;
		@ElementList(inline = true, entry = "row", required = false)
		public List<Skill> rows;
	}

	@Root(strict = false)
	public static final class Skill {
		@Attribute(required = false)
		public long typeID;
		@Attribute(required = false)
		public long skillpoints;
		@Attribute(required = false)
		public short level;
		@Attribute(required = false)
		public int published;
	}

	@Commit
	public void commit() {
		skills = new HashMap<>();
		if (result != null) {
			for (Rowset rowset : result.rowsets) {
				if ("skills".equals(rowset.name)) {
					for (Skill skill : rowset.rows) {
						if (skill.published == 1) {
							skills.put(skill.typeID, skill.level);
						}
					}
				}
			}
		}
	}

	public Map<Long, Short> getSkills() {
		return skills;
	}
}
