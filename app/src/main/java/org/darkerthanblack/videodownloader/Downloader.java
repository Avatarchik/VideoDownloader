package org.darkerthanblack.videodownloader;

import android.util.Log;

import org.darkerthanblack.videodownloader.entity.Bilibili;
import org.darkerthanblack.videodownloader.entity.Video;
import org.darkerthanblack.videodownloader.entity.Youku;

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
            List<String> fileUrl = v.getFileUrl(url,type);
            Log.v("Jay","fileUrl----->"+fileUrl);
        }

    }
}
