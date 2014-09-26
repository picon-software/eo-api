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

import fr.eo.api.model.ApiKeyInfo;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * @author picon.software
 */
public interface AccountService {

    @Headers("User-Agent: Eve online industrial tool")
    @GET("/account/APIKeyInfo.xml.aspx")
    ApiKeyInfo apiKeyInfo(
            @Query("keyID") long keyID,
            @Query("vCode") String vCode);

    @Headers("User-Agent: Eve online industrial tool")
    @GET("/account/APIKeyInfo.xml.aspx")
    void apiKeyInfo(
            @Query("keyID") long keyID,
            @Query("vCode") String vCode,
            Callback<ApiKeyInfo> callback);

}
