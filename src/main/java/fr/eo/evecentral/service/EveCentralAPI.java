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

package fr.eo.evecentral.service;

import fr.eo.evecentral.model.MarketStat;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * @author picon.software
 */
public interface EveCentralAPI {
    String URL = "http://api.eve-central.com";

    @Headers({"Cache-Control: max-age=3600",
            "User-Agent: Eve online industrial tool"})
    @GET("/api/marketstat")
    MarketStat marketstatFull(@Query("hours") Integer hours, @Query("minQ") Integer minQ, @Query("regionlimit") Integer regionlimit, @Query("usesystem") Integer usesystem, @Query("typeid") Integer... typeid);

    @Headers({"Cache-Control: max-age=60",
            "User-Agent: Eve online industrial tool"})
    @GET("/api/marketstat")
    MarketStat marketstat(@Query("regionlimit") Integer regionlimit, @Query("usesystem") Integer usesystem, @Query("typeid") Integer... typeid);

}
