package org.darkerthanblack.videodownloader;

import android.content.Context;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.axeldroid.Axel;

import org.darkerthanblack.videodownloader.entity.Bilibili;
import org.darkerthanblack.videodownloader.entity.Video;
import org.darkerthanblack.videodownloader.entity.VideoSite;
import org.darkerthanblack.videodownloader.entity.Youku;

import java.io.File;
import java.util.List;

/**
 * Created by Jay on 16/3/1.
 */
public class Downloader {
    public static Video download(Context c,String url ,String type){
        Axel axel;
        Video video = null;
        VideoSite videoSite = null;
        if(url.contains("bilibili")){
        //if(true){
            videoSite = new Bilibili();
        }else if(url.contains("youku")){
            videoSite = new Youku();
        }else {
            System.out.print("Error");
        }

        if(videoSite!=null){
            video = videoSite.getVideo(url, type);
            List<String> fileUrl = video.getFileUrlList();
            Log.v("Jay","video----->"+video);
            if(fileUrl!=null){
                axel = new Axel() {
                    @Override
                    protected void onProgress() {
                        // TODO Auto-generated method stub
                        super.onProgress();
                        Log.v("Jay", "progress(byte):" + bytes_done
                                + ",speed(byte/s):" + bytes_per_second
                                + ",time left(s):" + left_seconds);
                    }
                    @Override
                    protected void onFinish(int message) {
                        // TODO Auto-generated method stub
                        super.onFinish(message);

                        Log.v("Jay", "onfinished,size:" + bytes_done
                                + ",cost_seconds:" + cost_seconds);
                    }
                    @Override
                    protected void onStart() {
                        // TODO Auto-generated method stub
                        super.onStart();
                    }

                };
                File downloadroot = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + PreferenceManager
                        .getDefaultSharedPreferences(c)
                        .getString("default_file_location","VD"));
                if(!downloadroot.exists()){
                    downloadroot.mkdir();
                }
                axel.axel_new(downloadroot + File.separator + "a.flv",
                        new String[]{fileUrl.get(0)});
                axel.connections = 1;

                try {
                    axel.axel_start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return video;
    }
}
