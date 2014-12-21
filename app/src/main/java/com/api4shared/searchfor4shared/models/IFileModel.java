package com.api4shared.searchfor4shared.models;

/**
 * Created by stanislavsikorsyi on 21.12.14.
 */
public interface IFileModel {
    String getName();

    void setName(String name);

    String getThumbnailUrl();

    void setThumbnailUrl(String thumbnailUrl);

    String getDownloadPage();

    void setDownloadPage(String downloadPage);
}
