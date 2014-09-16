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

import java.util.ArrayList;
import java.util.List;

/**
 * @author picon.software
 */
@Root(strict = false)
public class ConquerableStationList extends ApiResult {

	private static final long serialVersionUID = -508261875818981165L;

	@Element(required = false)
	public Result result;

	private List<Station> stations;

	public ConquerableStationList() {
	}

	@Root(strict = false)
	public static final class Result {
		@ElementList(entry = "row")
		public List<Station> rowset;
	}

	@Root(strict = false)
	public static final class Station {
		@Attribute
		public long stationID;
		@Attribute
		public String stationName;
		@Attribute
		public long stationTypeID;
		@Attribute
		public long solarSystemID;
		@Attribute
		public long corporationID;
		@Attribute
		public String corporationName;
	}

	@Commit
	public void commit() {
		stations = new ArrayList<>();
		if (result != null) {
			for (Station station : result.rowset) {
				stations.add(station);
			}
		}
	}

	public List<Station> getStations() {
		return stations;
	}
}
