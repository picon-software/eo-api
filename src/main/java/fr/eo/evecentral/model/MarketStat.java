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

package fr.eo.evecentral.model;

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
public class MarketStat {

    @ElementList(name = "marketstat", entry = "type")
    public List<Type> types;

    private Map<Integer, Type> typeMap;

    @Root(strict = false)
    public static final class Type {
        @Attribute
        public int id;
        @Element
        public PriceInfo buy;
        @Element
        public PriceInfo sell;
    }

    @Root(strict = false)
    public static final class PriceInfo {
        @Element
        public long volume;
        @Element
        public double max;
        @Element
        public double min;
    }

    @Commit
    public void commit() {
        typeMap = new HashMap<>();

        for (Type type : types) {
            typeMap.put(type.id, type);
        }
    }

    public Type getTypeById(int id) {
        return typeMap.get(id);
    }
}
