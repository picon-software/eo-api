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
import java.util.Date;
import java.util.List;

/**
 * @author picon.software
 */
@Root(strict = false)
public class Jobs extends ApiResult {

    private static final long serialVersionUID = 6028784440525836602L;

    public Jobs() {
    }

    @Element(required = false)
    public Result result;

    private List<Job> jobs;

    @Root(strict = false)
    public static final class Result {
        @ElementList(entry = "row")
        public List<Job> rowset;
    }

    @Root(strict = false)
    public static final class Job {
        @Attribute
        public long facilityID;
        @Attribute
        public long stationID;
        @Attribute
        public long solarSystemID;
        @Attribute
        public long activityID;
        @Attribute
        public long blueprintTypeID;
        @Attribute
        public Date startDate;
        @Attribute
        public Date endDate;
    }

    @Commit
    public void commit() {
        jobs = new ArrayList<>();
        if (result != null) {
            for (Job job : result.rowset) {
                jobs.add(job);
            }
        }
    }

    public List<Job> getJobs() {
        return jobs;
    }
}
