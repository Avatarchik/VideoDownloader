package org.darkerthanblack.videodownloader.entity;

import org.darkerthanblack.videodownloader.utils.HttpUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jay on 16/3/4.
 */
public class Tudou implements VideoSite{
    private String baseUrl = "http://vr.tudou.com/v2proxy/v2.m3u8?it=";
    private String urlSuffix = "&s=0&e=10000000000";
    @Override
    public Video getVideo(String url, int type) {

        Video v = new Video();
        String page = HttpUtils.doGet(url);
        Matcher matcher = Pattern.compile("(?<=<title>).*(?=</title>)").matcher(page);
        if (matcher.find())
        {
            String name = matcher.group();
            v.setName(name);
        }
        matcher = Pattern.compile("(?<=,iid: )(.*)").matcher(page);
        if (matcher.find())
        {
            String videoID = matcher.group();
            v.setId(Integer.valueOf(videoID));
            String tempUrl = baseUrl + videoID;
            String tempResult = HttpUtils.doGet(tempUrl);
            matcher = Pattern.compile("(.*)(?=&s=0&e=)").matcher(tempResult);
            if(matcher.find()){
                Set tempSet = new HashSet<>();
                for(int i=0;i<matcher.groupCount();i++){
                    tempSet.add(matcher.group(i)+urlSuffix);
                }
                List<String> tempList = new ArrayList<>(tempSet);
                v.setFileUrlList(tempList);
                String extName = ".flv";
                matcher = Pattern.compile("\\.[a-z0-9]{3,5}(?=&)").matcher(tempList.get(0));
                if (matcher.find()) {
                    extName = matcher.group();
                }
                v.setExtName(extName);
            }
        }
        return v;
    }
}
