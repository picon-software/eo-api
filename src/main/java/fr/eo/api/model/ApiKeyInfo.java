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
public class ApiKeyInfo extends ApiResult {

    private static final long serialVersionUID = 7537262625587315747L;

    private List<CharacterInfo> characters;

    @Element(required = false)
    public Result result;

    public ApiKeyInfo() {
    }

    @Root(strict = false)
    public static final class Result {
        @Element
        public Key key;
    }

    @Root(strict = false)
    public static final class Key {
        @Attribute
        public long accessMask;
        @Attribute
        public String type;
        @ElementList(entry = "row")
        public List<CharacterInfo> rowset;
    }

    @Root(strict = false)
    public static final class CharacterInfo {
        public long accessMask;
        @Attribute
        public long characterID;
        @Attribute
        public String characterName;
        @Attribute
        public long corporationID;
        @Attribute
        public String corporationName;
    }

    @Commit
    public void commit() {
        characters = new ArrayList<>();
        if (result != null &&
                result.key != null &&
                result.key.rowset != null) {
            for (CharacterInfo characterInfo : result.key.rowset) {
                characterInfo.accessMask = result.key.accessMask;
                characters.add(characterInfo);
            }
        }
    }

    public List<CharacterInfo> getCharacters() {
        return characters;
    }
}