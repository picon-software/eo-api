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

package fr.eo.api;

import fr.eo.api.helper.AccessMaskHelper;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author picon.software
 */
public class AccessMaskHelperTest {

    @Test
    public void test() {
        long fullAccess = 268435455;
        long specifiedAccess = 524298;
        long invalidAccessMask = 10;

        assertTrue(AccessMaskHelper.isAccessible(fullAccess, AccessMaskHelper.CHARACTER_SHEET));
        assertTrue(AccessMaskHelper.isAccessible(fullAccess, AccessMaskHelper.ASSET_LIST));
        assertTrue(AccessMaskHelper.isAccessible(fullAccess, AccessMaskHelper.STANDINGS));

        assertTrue(AccessMaskHelper.isAccessible(specifiedAccess, AccessMaskHelper.CHARACTER_SHEET));
        assertTrue(AccessMaskHelper.isAccessible(specifiedAccess, AccessMaskHelper.ASSET_LIST));
        assertTrue(AccessMaskHelper.isAccessible(specifiedAccess, AccessMaskHelper.STANDINGS));

        assertTrue(AccessMaskHelper.isAccessible(invalidAccessMask, AccessMaskHelper.CHARACTER_SHEET));
        assertTrue(AccessMaskHelper.isAccessible(invalidAccessMask, AccessMaskHelper.ASSET_LIST));
        assertFalse(AccessMaskHelper.isAccessible(invalidAccessMask, AccessMaskHelper.STANDINGS));
    }
}
