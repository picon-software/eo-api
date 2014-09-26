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

package fr.eo.api.util;

import org.simpleframework.xml.transform.Transform;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author picon.software
 */
public class DateFormatTransformer implements Transform<Date> {
    private DateFormat dateFormat;

    public DateFormatTransformer(String dateFormat) {
        this.dateFormat = new SimpleDateFormat(dateFormat);
    }

    @Override
    public Date read(String value) throws Exception {
        return dateFormat.parse(value);
    }

    @Override
    public String write(Date value) throws Exception {
        return dateFormat.format(value);
    }
}