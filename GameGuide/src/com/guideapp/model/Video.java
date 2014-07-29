package com.guideapp.model;

import android.net.Uri;

/**
 * User: Khiemvx
 * Date: 7/27/14
 */
public class Video
{
    private Uri url;
    private String description;

    public Video(Uri url, String description)
    {
        this.url = url;
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Uri getUrl()
    {
        return url;
    }

    public void setUrl(Uri url)
    {
        this.url = url;
    }
}
