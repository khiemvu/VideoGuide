package com.guideapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import com.guideapp.R;
import com.guideapp.adapter.VideoAdapter;
import com.guideapp.utils.Constant;
import com.guideapp.utils.Utils;

import java.util.ArrayList;

public class VideoViewActivity extends Activity
{
    ListView listView;
    public ProgressDialog pDialog;
    ArrayList<Object> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Get the layout from video_main.xml
        setContentView(R.layout.activity_video_view_main);
        // Find your VideoView in your video_main.xml layout
        listView = (ListView) findViewById(R.id.lvContent);
        // Create a progressbar
//        pDialog = new ProgressDialog(VideoViewActivity.this);
//        // Set progressbar title
//        pDialog.setTitle("Android Video Streaming Tutorial");
//        // Set progressbar message
//        pDialog.setMessage("Buffering...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        // Show progressbar
//        pDialog.show();
        String screenType = getIntent().getExtras().getString(Constant.SCREEN_TYPE);
        arrayList = Utils.getDataFromJsonFile(VideoViewActivity.this, screenType);

//        if (arrayList.size() == 3)
//        {
//            pDialog.dismiss();
        VideoAdapter videoAdapter = new VideoAdapter(VideoViewActivity.this, arrayList);
        listView.setAdapter(videoAdapter);
//        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(VideoViewActivity.this, LaunchActivity.class);
        startActivity(intent);
        finish();
    }
}
