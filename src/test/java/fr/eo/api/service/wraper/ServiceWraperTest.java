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

package fr.eo.api.service.wraper;

import fr.eo.api.model.ApiResult;
import fr.eo.api.services.wraper.ServiceWraper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author picon.software
 */
public class ServiceWraperTest {

    private static File cacheDir;

    @BeforeClass
    public static void beforeTest() {
        cacheDir = org.fest.util.Files.newTemporaryFolder();
    }

    @Test
    public void test() {
        ServiceWraper serviceWraper = new ServiceWraper(cacheDir);

        TestResult result = serviceWraper.invoke(new ServiceWraper.Callable<TestResult>() {
            @Override
            public String cacheKey() {
                return "givenResult1";
            }

            @Override
            public TestResult call() {
                return givenResult(1, 10000);
            }
        });

        assertThat(result).isNotNull();
        assertThat(result.getValue()).isEqualTo(1);

        result = serviceWraper.invoke(new ServiceWraper.Callable<TestResult>() {
            @Override
            public String cacheKey() {
                return "givenResult1";
            }

            @Override
            public TestResult call() {
                return givenResult(2, 10000);
            }
        });

        assertThat(result).isNotNull();
        assertThat(result.getValue()).isEqualTo(1);

        serviceWraper.invoke(new ServiceWraper.Callable<TestResult>() {
            @Override
            public String cacheKey() {
                return "givenResult2";
            }

            @Override
            public TestResult call() {
                return givenResult(2, -1);
            }
        });

        result = serviceWraper.invoke(new ServiceWraper.Callable<TestResult>() {
            @Override
            public String cacheKey() {
                return "givenResult2";
            }

            @Override
            public TestResult call() {
                return givenResult(2, 1);
            }
        });

        assertThat(result).isNotNull();
        assertThat(result.getValue()).isEqualTo(2);
    }

    private TestResult givenResult(int value, int validity) {
        return new TestResult(value, validity);
    }

    private static class TestResult extends ApiResult {

        private static final long serialVersionUID = -3989337475354971166L;

        private int value;

        private TestResult(int value, int validity) {
            this.value = value;
            this.currentTime = new Date();
            this.cachedUntil = new Date(currentTime.getTime() + validity);
        }

        public int getValue() {
            return value;
        }
    }
}
