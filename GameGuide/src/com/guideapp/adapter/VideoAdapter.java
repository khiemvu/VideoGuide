package com.guideapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import com.guideapp.R;
import com.guideapp.model.Video;

import java.util.ArrayList;

/**
 * User: Khiemvx
 * Date: 7/27/14
 */
public class VideoAdapter extends BaseAdapter
{
    private Activity activity;
    private ArrayList<Object> videos = new ArrayList<Object>();

    public VideoAdapter(Activity activity, ArrayList<Object> videos)
    {
        this.activity = activity;
        this.videos = videos;
    }

    @Override
    public int getCount()
    {
        return videos.size();
    }

    @Override
    public Object getItem(int position)
    {
        return videos.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Log.e("All in one", "===> position " + position);
        View v = convertView;
        final ViewHolder holder;
        if (v == null)
        {
            LayoutInflater mInflater = (LayoutInflater) activity.getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = mInflater.inflate(R.layout.activity_video_view_main_item, parent, false);
            holder = new ViewHolder();
            holder.videoView = (VideoView) v.findViewById(R.id.vvItem);
            holder.tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }
        Video video = (Video) videos.get(position);
        holder.tvDescription.setText(video.getDescription());
        try
        {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    activity);
            mediacontroller.setAnchorView(holder.videoView);
            // Get the URL from String VideoURL
            holder.videoView.setMediaController(mediacontroller);
            holder.videoView.setVideoURI(video.getUrl());

        }
        catch (Exception e)
        {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp)
            {
                holder.videoView.seekTo(3);
            }
        });

        return v;
    }

    class ViewHolder
    {
        VideoView videoView;
        TextView tvDescription;
    }
}
