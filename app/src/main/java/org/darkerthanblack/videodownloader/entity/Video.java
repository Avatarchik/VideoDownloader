package org.darkerthanblack.videodownloader.entity;

import java.util.List;

/**
 * Created by Jay on 16/3/2.
 */
public class Video {
    private int id;
    private String name;
    private List<String> fileUrlList;
    private int downloadState;
    private int size;
    private String extName;

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public int getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(int downloadState) {
        this.downloadState = downloadState;
    }

    public List<String> getFileUrlList() {
        return fileUrlList;
    }

    public void setFileUrlList(List<String> fileUrlList) {
        this.fileUrlList = fileUrlList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Video{" +
                "downloadState=" + downloadState +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", fileUrlList=" + fileUrlList +
                ", size=" + size +
                ", extName='" + extName + '\'' +
                '}';
    }
}
