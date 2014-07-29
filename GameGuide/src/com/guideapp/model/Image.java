package com.guideapp.model;

/**
 * User: khiemvx
 * Date: 7/28/14
 */
public class Image
{
    private String url;
    private String description;

    public Image(String url, String description)
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

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
