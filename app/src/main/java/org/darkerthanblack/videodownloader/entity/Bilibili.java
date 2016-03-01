package org.darkerthanblack.videodownloader.entity;

import org.darkerthanblack.videodownloader.utils.HttpUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jay on 16/3/1.
 */
public class Bilibili implements Video {
    private String baseUrl = "http://interface.bilibili.com/playurl?player=2&sign=5b790eb0d593597d1964425c4d9691df&otype=json";

    public Bilibili(){

    }
    @Override
    public String getFileUrl(String url ,String type) {
        String result = null;
        String page = HttpUtils.doGet(url);
        //System.out.println(page);
        Matcher matcher = Pattern.compile("cid=(\\d*)").matcher(page);
        if (matcher.find())
        {
            String cid = matcher.group();
            System.out.println(cid);
            String videoUrl = baseUrl + "&" + cid + "&ts=" + (new Date().getTime()/1000);
            System.out.println(videoUrl);
            String jsonResult = HttpUtils.doGet(videoUrl);
            System.out.println(jsonResult);
            try {
                JSONArray ja= new JSONArray(jsonResult);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
