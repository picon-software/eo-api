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
public class Standings extends ApiResult {

    private static final long serialVersionUID = -7574836860681161750L;

    @Element(required = false)
    public Result result;

    private Map<Long, Float> standings;

    public Standings() {
    }

    @Root(strict = false)
    public static final class Result {

        @ElementList(entry = "rowset")
        public List<NpcStandings> characterNPCStandings;
    }

    @Root(strict = false)
    public static final class NpcStandings {

        @Attribute
        public String name;

        @ElementList(entry = "row", required = false, inline = true)
        public List<Standing> row;
    }

    @Root(strict = false)
    public static final class Standing {

        @Attribute
        public long fromID;
        @Attribute
        public float standing;
    }


    @Commit
    public void commit() {
        standings = new HashMap<>();

        if (result != null && result.characterNPCStandings != null) {
            for (NpcStandings characterNPCStanding : result.characterNPCStandings) {
                if (characterNPCStanding.row != null) {
                    for (Standing standing : characterNPCStanding.row) {
                        standings.put(standing.fromID, standing.standing);
                    }
                }
            }
        }
    }

    public Map<Long, Float> getStandings() {
        return standings;
    }
}
