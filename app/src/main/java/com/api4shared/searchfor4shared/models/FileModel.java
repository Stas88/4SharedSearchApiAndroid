package com.api4shared.searchfor4shared.models;

/**
 * Model for File searched with 4Shared API
 */
public class FileModel implements IFileModel {

    private String name;
    private String thumbnailUrl;
    private String downloadPage;

    public FileModel() {}

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Override
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String getDownloadPage() {
        return downloadPage;
    }

    @Override
    public void setDownloadPage(String downloadPage) {
        this.downloadPage = downloadPage;
    }

}

