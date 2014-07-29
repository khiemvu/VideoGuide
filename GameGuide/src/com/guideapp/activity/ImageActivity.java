package com.guideapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import com.guideapp.R;
import com.guideapp.adapter.ImageAdapter;
import com.guideapp.utils.Utils;

/**
 * User: khiemvx
 * Date: 7/28/14
 */
public class ImageActivity extends Activity
{
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Get the layout from video_main.xml
        setContentView(R.layout.activity_video_view_main);
        // Find your VideoView in your video_main.xml layout
        listView = (ListView) findViewById(R.id.lvContent);
        ImageAdapter videoAdapter = new ImageAdapter(ImageActivity.this, Utils.getDataFromJsonFile(ImageActivity.this, "image"));
        listView.setAdapter(videoAdapter);
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(ImageActivity.this, LaunchActivity.class);
        startActivity(intent);
        finish();
    }
}
