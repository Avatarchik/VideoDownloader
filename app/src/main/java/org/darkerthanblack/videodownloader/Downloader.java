package org.darkerthanblack.videodownloader;

import android.os.Environment;
import android.util.Log;

import com.axeldroid.Axel;

import org.darkerthanblack.videodownloader.entity.Bilibili;
import org.darkerthanblack.videodownloader.entity.Video;
import org.darkerthanblack.videodownloader.entity.Youku;

import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Jay on 16/3/1.
 */
public class Downloader {
    public static void download(String url ,String type){

        Video v = null;
        if(url.contains("bilibili")){
        //if(true){
            v = new Bilibili();
        }else if(url.contains("youku")){
            v = new Youku();
        }else {
            System.out.print("Error");
        }

        if(v!=null){
            List<String> fileUrl = v.getFileUrl(url, type);
            Log.v("Jay","fileUrl----->"+fileUrl);
            if(fileUrl!=null){
                Axel axel = new Axel() {
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
                Log.v("Jay", Environment.getExternalStorageDirectory().getPath() + File.separator + "VDtemp/a.flv");
                File downloadroot = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "VDtemp");
                if(!downloadroot.exists()){
                    downloadroot.mkdir();
                }
                axel.axel_new(Environment.getExternalStorageDirectory().getPath() + File.separator + "VDtemp/a.flv",
                        new String[]{fileUrl.get(0)});
                axel.connections = 1;

                try {
                    axel.axel_start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
