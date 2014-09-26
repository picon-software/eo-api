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
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.io.Serializable;
import java.util.Date;

/**
 * @author picon.software
 */
@Root(strict = false)
public abstract class ApiResult implements Serializable {

    @Element
    public Date currentTime;
    @Element
    public Date cachedUntil;
    @Element(required = false)
    public Error error;

    public Date responseTime = new Date();

    @Root(strict = false)
    public static final class Error {
        @Attribute
        public int code;
        @Text
        public String label;
    }

    public Date localCachedUntil() {
        long maxAge = cachedUntil.getTime() - currentTime.getTime();
        return new Date(responseTime.getTime() + maxAge);
    }
}
