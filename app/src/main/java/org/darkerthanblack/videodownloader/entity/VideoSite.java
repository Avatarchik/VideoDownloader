package org.darkerthanblack.videodownloader.entity;

/**
 * Created by Jay on 16/3/1.
 */
public interface VideoSite {
    Video getVideo(String url, int type);
}
