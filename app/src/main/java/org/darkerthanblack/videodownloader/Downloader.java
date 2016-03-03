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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jay on 16/3/1.
 */
public class Downloader {
    public static final int STATUS_DOWNLOADING = 0;
    public static final int STATUS_DOWNLOADED = 1;
    public static final int STATUS_PAUSE = 2;
    public static final int STATUS_NEW = 3;

    public int downloadStatus = STATUS_NEW;
    /**
     * 已下载字节数
     */
    public long bytesDone;

    /**
     * 文件总大小
     */
    public long fileSize;

    /**
     * 当前下载速度，
     */
    public int bytesPerSecond;

    /**
     * 离下载完成剩下的时间
     */
    public int leftSeconds;

    /**
     * 下载完成消耗的总时间
     */
    public int costSeconds;

    public String url;
    public String type;
    public Context context;


    public Downloader(Context c,String url ,String type){
        this.context = c;
        this.url = url;
        this.type = type;
    }

    public Video download(){
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
                        .getDefaultSharedPreferences(context)
                        .getString("default_file_location","VD"));
                if(!downloadroot.exists()){
                    downloadroot.mkdir();
                }
                String tempFileUrl = fileUrl.get(0);
                String extName = ".flv";
                Matcher matcher = Pattern.compile("\\.[a-z0-9]{3,5}(?=\\?)").matcher(tempFileUrl);
                if (matcher.find()) {
                    extName = matcher.group();
                }
                video.setExtName(extName);
                axel.axel_new(downloadroot + File.separator + video.getId()+extName,
                        new String[]{tempFileUrl});
                axel.connections = 1;

                try {
                    axel.axel_start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Log.v("Jay","video----->"+video);
        return video;
    }
}
