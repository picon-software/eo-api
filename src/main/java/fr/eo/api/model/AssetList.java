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
public class AssetList extends ApiResult {

    public static final int ASSET_FLAG_HANGAR = 4;
    private static final long serialVersionUID = 6028784440525836602L;

    @Element(required = false)
    public Result result;

    private Map<Long, Map<Long, Long>> assets;

    public AssetList() {
    }

    @Root(strict = false)
    public static final class Result {

        @ElementList(entry = "row")
        public List<Asset> rowset;
    }

    @Root(strict = false)
    public static final class Asset {

        @Attribute(required = false)
        public long locationID;
        @Attribute
        public long typeID;
        @Attribute
        public long quantity;
        @Attribute
        public long flag;
        @ElementList(entry = "row", required = false)
        public List<Asset> rowset;
    }

    @Commit
    public void commit() {
        assets = new HashMap<>();
        if (result != null && result.rowset != null) {
            for (Asset asset : result.rowset) {
                if (asset.flag == ASSET_FLAG_HANGAR) {
                    if (!assets.containsKey(asset.locationID)) {
                        assets.put(asset.locationID, new HashMap<Long, Long>());
                    }
                    if (assets.get(asset.locationID).containsKey(asset.typeID)) {
                        long newQuantity = assets.get(asset.locationID).get(asset.typeID) + asset.quantity;
                        assets.get(asset.locationID).put(asset.typeID, newQuantity);
                    } else {
                        assets.get(asset.locationID).put(asset.typeID, asset.quantity);
                    }
                }
            }
        }
    }

    public Map<Long, Map<Long, Long>> getAssets() {
        return assets;
    }
}
