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

package fr.eo.api.services.wraper;

import com.squareup.okhttp.internal.DiskLruCache;
import com.squareup.okhttp.internal.Util;
import fr.eo.api.model.ApiResult;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * @author picon.software
 */
public class ServiceWraper {

    private DiskLruCache cache;

    public ServiceWraper(File cacheDir) {
        try {
            cache = DiskLruCache.open(cacheDir, 1, 1, 5 * 1024 * 1024);
        } catch (IOException e) {
            // can't initialize cache -> fallback to cacheless mode
            cache = null;
        }
    }

    protected boolean cacheContains(String key) {
        if (cache == null) {
            return false;
        }

        try {
            return cache.get(key) != null;
        } catch (IOException e) {
            return false;
        }
    }

    public <R extends ApiResult> R invoke(Callable<R> callable) {
        String key = Util.hash(callable.cacheKey());
        R result;
        if (cacheContains(key)) {
            //noinspection unchecked
            result = (R) getFromCache(key);
            if (new Date().before(result.localCachedUntil())) {
                return result;
            } else {
                removeFromCache(key);
            }
        }

        result = callable.call();
        putIntoCache(key, result);

        return result;
    }

    private boolean removeFromCache(String key) {
        try {
            return cache.remove(key);
        } catch (IOException e) {
            return false;
        }
    }

    private ApiResult getFromCache(String key) {
        if (cache == null) {
            return null;
        }

        DiskLruCache.Snapshot snapshot;
        ApiResult result = null;
        try {
            snapshot = cache.get(key);
        } catch (IOException e) {
            return null;
        }

        if (snapshot == null) {
            return null;
        }

        try {
            result = readResult(snapshot.getInputStream(0));
        } catch (IOException | ClassNotFoundException e) {
            Util.closeQuietly(snapshot);
        }

        return result;
    }

    private ApiResult readResult(InputStream is) throws IOException, ClassNotFoundException {
        if (is == null) {
            return null;
        }

        ObjectInputStream ois = new ObjectInputStream(is);
        return (ApiResult) ois.readObject();
    }

    private void putIntoCache(String key, ApiResult result) {
        if (cache == null) {
            return;
        }

        DiskLruCache.Editor editor = null;
        try {
            editor = cache.edit(key);
            writeResult(result, editor.newOutputStream(0));
            editor.commit();
        } catch (IOException e) {
            abortQuietly(editor);
        }
    }

    private void writeResult(ApiResult result, OutputStream os) throws IOException {
        if (os != null) {
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(os));
            oos.writeObject(result);
            oos.flush();
            oos.close();
        }
    }

    private void abortQuietly(DiskLruCache.Editor editor) {
        // Give up because the cache cannot be written.
        try {
            if (editor != null) {
                editor.abort();
            }
        } catch (IOException ignored) {
        }
    }

    public interface Callable<R extends ApiResult> {
        String cacheKey();

        R call();
    }
}
