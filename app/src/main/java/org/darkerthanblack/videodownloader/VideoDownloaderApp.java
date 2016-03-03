package org.darkerthanblack.videodownloader;

import android.app.Application;

import org.xutils.x;
/**
 * Created by Jay on 16/3/3.
 */
public class VideoDownloaderApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
