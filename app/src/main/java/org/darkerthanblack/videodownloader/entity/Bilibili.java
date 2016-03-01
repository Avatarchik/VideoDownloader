package org.darkerthanblack.videodownloader.entity;

import android.util.Log;

import org.darkerthanblack.videodownloader.utils.HttpUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public List<String> getFileUrl(String url ,String type) {
        if(url.contains("mobile")){
            url = "http://www.bilibili.com/video/av"+url.replaceAll("\\D*","");
        }
        List<String> result = new ArrayList<>();
        String page = HttpUtils.doGet(url);
        if(page!=null) {
            //System.out.println(page);
            Matcher matcher = Pattern.compile("cid=(\\d*)").matcher(page);
            if (matcher.find()) {
                String cid = matcher.group();
                String videoUrl = baseUrl + "&" + cid + "&ts=" + (new Date().getTime() / 1000);
                //System.out.println(videoUrl);
                String jsonResult = HttpUtils.doGet(videoUrl);
                //System.out.println(jsonResult);
                try {
                    JSONArray jsonArray = new JSONObject(jsonResult).getJSONArray("durl");
                    for(int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
                        //System.out.println("Nub------"+i+"-------"+jsonObject);
                        JSONArray tempUrl = jsonObject.getJSONArray("backup_url");
                        if("s".equals(type)){
                            if(null!=tempUrl){
                                result.add(tempUrl.get(0).toString());
                            }else{
                                result.add(jsonObject.getString("url"));
                            }
                        }else if("h".equals(type)){
                            if(null!=tempUrl) {
                                if (tempUrl.length() > 1) {
                                    result.add(tempUrl.get(1).toString());
                                } else {
                                    result.add(tempUrl.get(0).toString());
                                }
                            }else {
                                result.add(jsonObject.getString("url"));
                            }
                        }else{
                            result.add(jsonObject.getString("url"));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
