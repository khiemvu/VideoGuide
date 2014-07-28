package com.guideapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.guideapp.R;
import com.guideapp.model.Image;
import com.guideapp.utils.ImageDownloaderTask;

import java.util.ArrayList;

/**
 * User: khiemvx
 * Date: 7/28/14
 */
public class ImageAdapter extends BaseAdapter
{
    private Activity activity;
    private ArrayList<Image> images = new ArrayList<Image>();

    public ImageAdapter(Activity activity, ArrayList<Image> images)
    {
        this.activity = activity;
        this.images = images;
    }

    @Override
    public int getCount()
    {
        return images.size();
    }

    @Override
    public Object getItem(int position)
    {
        return images.get(position);
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
            v = mInflater.inflate(R.layout.activity_image_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) v.findViewById(R.id.ivImage);
            holder.tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }

        Image image = images.get(position);

//        ImageLoader imgLoader = new ImageLoader(activity.getApplicationContext());
//        imgLoader.DisplayImage(url, loader, holder.imageView);


        if (holder.imageView != null)
        {
            new ImageDownloaderTask(holder.imageView).execute(image.getUrl());
        }

        holder.tvDescription.setText(image.getDescription());


        return v;
    }

    class ViewHolder
    {
        ImageView imageView;
        TextView tvDescription;
    }
}
