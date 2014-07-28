package com.guideapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.guideapp.R;
import com.guideapp.adapter.ImageAdapter;
import com.guideapp.model.Image;

import java.util.ArrayList;

/**
 * User: khiemvx
 * Date: 7/28/14
 */
public class ImageActivity extends Activity
{
    ListView listView;
    ArrayList<Image> arrayList;
    // Image url
    String image_url = "http://dantri.vcmedia.vn/Uploaded/2009/06/26/ngoc5266009.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Get the layout from video_main.xml
        setContentView(R.layout.activity_video_view_main);
        // Find your VideoView in your video_main.xml layout
        listView = (ListView) findViewById(R.id.lvContent);
        ImageAdapter videoAdapter = new ImageAdapter(ImageActivity.this, fakeData());
        listView.setAdapter(videoAdapter);
    }

    public ArrayList<Image> fakeData()
    {
        arrayList = new ArrayList<Image>();
        Image image = new Image(image_url, "This is My Description");
        Image image1 = new Image(image_url, "This is content");
        Image image2 = new Image(image_url, "This is content");
        Image image3 = new Image(image_url, "This is content");
        arrayList.add(image);
        arrayList.add(image1);
        arrayList.add(image2);
        arrayList.add(image3);

        return arrayList;
    }
}
