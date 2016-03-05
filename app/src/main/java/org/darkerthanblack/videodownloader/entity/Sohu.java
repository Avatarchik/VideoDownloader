package org.darkerthanblack.videodownloader.entity;

import android.util.Log;

import org.darkerthanblack.videodownloader.utils.HttpUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jay on 16/3/4.
 */
public class Sohu implements VideoSite {
    private String baseUrl ="http://m.tv.sohu.com/phone_playinfo?callback=jsonpx1&vid=";
    private String fileUrlSuffix = ".m3u8?plat=15&pt=6&prod=ott&pg=1&ch=v&qd=816";
    private String urlSuffix = "&start=0&end=10000000000&";
    @Override
    public Video getVideo(String url, int type) {
        String videoType;
        switch (type){
            case VideoType.S:
                videoType = "sup";
                break;
            case VideoType.HD:
                videoType = "hig";
                break;
            case VideoType.N:
                videoType = "nor";
                break;
            default:
                videoType = "hig";
                break;
        }
        Video v = new Video();
        String page = HttpUtils.doGet(url);
        if(page!=null) {
            Matcher matcher = Pattern.compile("(?<=<title>).*(?=</title>)").matcher(page);
            if (matcher.find()) {
                String name = matcher.group();
                v.setName(name);
            }
            matcher = Pattern.compile("\\s+vid=[\"'].*(?=\\s*[\"'])").matcher(page);
            if (matcher.find()) {
                String videoID = matcher.group().substring(matcher.group().indexOf("\"") + 1);
                v.setId(Integer.valueOf(videoID));
                String videoFileUrl = getVideoFileUrl(videoID, 1, videoType);
                if (videoFileUrl != null) {
                    String data = HttpUtils.doGet(videoFileUrl);
                    Log.v("Jay", "-----date-----\n" + data);
                    matcher = Pattern.compile("http://(.*)\\s+?").matcher(data);
                    List <String>list = new ArrayList();
                    while (matcher.find()) {
                        list.add(matcher.group().replaceAll("&start=.*?&end=.*?&", urlSuffix));
                    }
                    List<String> tempList = new ArrayList<>(new LinkedHashSet<>(list));
                    Log.v("Jay", "-----List-----\n" + tempList);
                    v.setFileUrlList(tempList);
                    String extName = ".flv";
                    matcher = Pattern.compile("\\.[a-z0-9]{3,5}(?=&)").matcher(tempList.get(0));
                    if (matcher.find()) {
                        extName = matcher.group();
                    }
                    v.setExtName(extName);
                }
            }
        }
        return v;
    }

    private String getVideoFileUrl(String url ,int site,String type){
        String result = null;
        String tempUrl = baseUrl + url +"&site="+site;
        String tempResult = HttpUtils.doGet(tempUrl);
        Matcher matcher = Pattern.compile("^.*\\((.*)(?=\\);)").matcher(tempResult);
        if(matcher.find()){
            String info = matcher.group().substring(matcher.group().indexOf("(") + 1);
            try {
                JSONObject m3u8Object = new JSONObject(info).getJSONObject("data").getJSONObject("urls").getJSONObject("m3u8");
                String m3u8Url = m3u8Object.getJSONArray(type).get(0).toString();
                if(m3u8Url==null||m3u8Url.length()<=0){
                    m3u8Url = m3u8Object.getJSONArray("nor").get(0).toString();
                }
                matcher = Pattern.compile("^(.*)(?=\\.m3u8\\?)").matcher(m3u8Url);
                if(matcher.find()){
                    result = matcher.group()+ fileUrlSuffix;
                }

            } catch (JSONException e) {
                result = getVideoFileUrl(url,2,type);
            }
        }
        return result;
    }
}
