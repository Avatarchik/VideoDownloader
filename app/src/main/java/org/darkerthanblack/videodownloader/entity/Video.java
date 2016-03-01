package org.darkerthanblack.videodownloader.entity;

import java.util.List;

/**
 * Created by Jay on 16/3/1.
 */
public interface Video {
    List<String> getFileUrl(String url,String type);
}
