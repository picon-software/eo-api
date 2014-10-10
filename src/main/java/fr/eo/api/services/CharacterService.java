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

package fr.eo.api.services;

import fr.eo.api.model.AssetList;
import fr.eo.api.model.CharacterSheet;
import fr.eo.api.model.Jobs;
import fr.eo.api.model.Standings;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * @author picon.software
 */
public interface CharacterService {

    @Headers("User-Agent: Eve online industrial tool")
    @GET("/char/CharacterSheet.xml.aspx")
    CharacterSheet characterSheet(
            @Query("keyID") long keyID,
            @Query("vCode") String vCode,
            @Query("characterID") long characterID);

    @Headers("User-Agent: Eve online industrial tool")
    @GET("/char/AssetList.xml.aspx")
    AssetList assetList(
            @Query("keyID") long keyID,
            @Query("vCode") String vCode,
            @Query("characterID") long characterID);


    @Headers("User-Agent: Eve online industrial tool")
    @GET("/char/Standings.xml.aspx")
    Standings standings(
            @Query("keyID") long keyID,
            @Query("vCode") String vCode,
            @Query("characterID") long characterID);

    @Headers("User-Agent: Eve online industrial tool")
    @GET("/char/IndustryJobs.xml.aspx")
    Jobs industryJobs(
            @Query("keyID") long keyID,
            @Query("vCode") String vCode,
            @Query("characterID") long characterID);
}
