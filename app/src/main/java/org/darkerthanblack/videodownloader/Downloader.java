package org.darkerthanblack.videodownloader;

import android.content.Context;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;


import org.darkerthanblack.videodownloader.download.DownloadManager;
import org.darkerthanblack.videodownloader.download.DownloadViewHolder;
import org.darkerthanblack.videodownloader.entity.Bilibili;
import org.darkerthanblack.videodownloader.entity.Video;
import org.darkerthanblack.videodownloader.entity.VideoSite;
import org.darkerthanblack.videodownloader.entity.Youku;
import org.xutils.ex.DbException;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jay on 16/3/1.
 */
public class Downloader {

    public String url;
    public String type;
    public Context context;

    public Downloader(Context c,String url ,String type){
        this.context = c;
        this.url = url;
        this.type = type;
    }

    public Video download(){
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
            if(fileUrl!=null){
                String downloadroot = Environment.getExternalStorageDirectory().getPath() + File.separator + PreferenceManager
                        .getDefaultSharedPreferences(context)
                        .getString("default_file_location","VD");
                File downloadrootDir = new File(downloadroot);
                if(!downloadrootDir.exists()){
                    downloadrootDir.mkdir();
                }
                for(int i=0 ;i<fileUrl.size();i++) {
                    String tempFileUrl = fileUrl.get(i);
                    String extName = ".flv";
                    Matcher matcher = Pattern.compile("\\.[a-z0-9]{3,5}(?=\\?)").matcher(tempFileUrl);
                    if (matcher.find()) {
                        extName = matcher.group();
                    }
                    video.setExtName(extName);
                    try {
                        DownloadManager.getInstance().startDownload(tempFileUrl,video.getId()+"-"+i,downloadroot+File.separator+video.getId()+"-"+i+video.getExtName(),true,true,null);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        Log.v("Jay","video----->"+video);
        return video;
    }
}
