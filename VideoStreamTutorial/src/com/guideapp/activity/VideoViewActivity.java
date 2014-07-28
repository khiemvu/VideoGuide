package com.guideapp.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import com.guideapp.R;
import com.guideapp.adapter.VideoAdapter;
import com.guideapp.model.Video;

import java.util.ArrayList;

public class VideoViewActivity extends Activity
{
    ListView listView;
    ArrayList<Video> arrayList;
    // Insert your Video URL
    String VideoURL = "https://dl.dropbox.com/s/ev2qqk4qzbuw7mv/Video%2B%20D%C3%A2n%20m%E1%BA%A1ng%20ph%C3%A1t%20%E2%80%9Cs%E1%BB%91t%E2%80%9D%20v%E1%BB%9Bi%20c%C3%B4%20b%C3%A9%20VN%20h%C3%A1t%20%E2%80%9CThe%20Show%E2%80%9D%20hay%20h%C6%A1n%20c%E1%BA%A3%20Lenka%2B%2B%20Bu%C3%B4nChuy%E1%BB%87n%20InFo%2B%2B%20thu%20gian%2B%2B%20giai%20tri%2B%2B%20xem%20phim%2B%2B%20ngh.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Get the layout from video_main.xml
        setContentView(R.layout.activity_video_view_main);
        // Find your VideoView in your video_main.xml layout
        listView = (ListView) findViewById(R.id.lvContent);

        VideoAdapter videoAdapter = new VideoAdapter(VideoViewActivity.this, fakeData());
        listView.setAdapter(videoAdapter);
    }

    public ArrayList<Video> fakeData()
    {
        arrayList = new ArrayList<Video>();
        Uri pathVideo = Uri.parse(VideoURL);
        Video video = new Video(pathVideo, "This is description");
        Video video1 = new Video(Uri.parse("https://dl.dropbox.com/s/wbdq62tbihfg0g1/Nhung%20pha%20bong%20hai%20huoc%20nhat.mp4"), "This is My Description");
        Video video2 = new Video(Uri.parse("https://dl.dropbox.com/s/f6ot3cpopu9fxhe/Ronaldinho%20-%20Nh%E1%BB%AFng%20pha%20bong%20k%E1%BB%B9%20thu%E1%BA%ADt%20c%E1%BB%A7a%20Ronaldinho.mp4"), "This is content");
        arrayList.add(video);
        arrayList.add(video1);
        arrayList.add(video2);

        return arrayList;
    }
}
