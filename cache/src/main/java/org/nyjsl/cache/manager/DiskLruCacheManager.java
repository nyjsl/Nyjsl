package org.nyjsl.cache.manager;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jakewharton.disklrucache.DiskLruCache;

import org.nyjsl.cache.Cache;
import org.nyjsl.cache.util.FileGenerator;
import org.nyjsl.cache.util.Utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import static org.nyjsl.cache.util.Utils.getAppVersion;

/**
 * Created by pc on 2017/3/20.
 */

public class DiskLruCacheManager<T extends Cache> implements CacheManager<T> {

    private static final int DEFAULT_VALUE_COUNT = 1;
    private static final int DEFAULT_MAX_SIZE = 50 * 1024 * 1024;


    @Override
    public void create(Context context, T t) {
        DiskLruCache mDiskLruCache;
        try {
            File cacheDir = getCacheDir(context);
            mDiskLruCache = getDiskLruCache(context, cacheDir);
            DiskLruCache.Editor editor = mDiskLruCache.edit(FileGenerator.generateUniqueFileName(t));
            OutputStream timeOs = editor.newOutputStream(0);
            OutputStream timeBos = new BufferedOutputStream(timeOs);
            ObjectOutputStream timeOos = new ObjectOutputStream(timeBos);
            timeOos.writeObject(t);
            editor.commit();
            Utils.closeQuietly(timeBos);
            Utils.closeQuietly(timeOos);
            Utils.closeQuietly(mDiskLruCache);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public T read(Context context, T t) {
        DiskLruCache mDiskLruCache;
        try {
            File cacheDir = getCacheDir(context);
            mDiskLruCache = getDiskLruCache(context, cacheDir);
            DiskLruCache.Snapshot editor = mDiskLruCache.get(FileGenerator.generateUniqueFileName(t));
            if (editor == null) {
                return null;
            }
            ObjectInputStream inputStream = new ObjectInputStream(editor.getInputStream(0));
            T result = (T) inputStream.readObject();
            Utils.closeQuietly(inputStream);
            Utils.closeQuietly(editor);
            Utils.closeQuietly(mDiskLruCache);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void update(Context context, T t) {
        create(context,t);
    }

    @Override
    public void delete(Context context, T t) {
        DiskLruCache mDiskLruCache;
        try {
            File cacheDir = getCacheDir(context);
            mDiskLruCache = getDiskLruCache(context, cacheDir);
            mDiskLruCache.remove(FileGenerator.generateUniqueFileName(t));
            Utils.closeQuietly(mDiskLruCache);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private DiskLruCache getDiskLruCache(Context context, File cacheDir) throws IOException {
        DiskLruCache mDiskLruCache;
        mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), DEFAULT_VALUE_COUNT, DEFAULT_MAX_SIZE);
        return mDiskLruCache;
    }

    @NonNull
    private File getCacheDir(Context context) {
        File cacheDir = FileGenerator.getDefaultCacheDirectory(context);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir;
    }

}
