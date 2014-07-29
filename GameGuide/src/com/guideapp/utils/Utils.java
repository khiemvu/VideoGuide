package com.guideapp.utils;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import com.guideapp.R;
import com.guideapp.activity.LaunchActivity;
import com.guideapp.model.Image;
import com.guideapp.model.Video;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * User: khiemvx
 * Date: 7/28/14
 */
public class Utils
{
    public static ArrayList<Object> getDataFromJsonFile(Activity activity, String typeObject)
    {
        String jsons;

        ArrayList<Object> arrayList = new ArrayList<Object>();
        // Reading text file from assets folder
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new InputStreamReader(activity.getAssets().open(
                    "data.txt")));
            String temp;
            while ((temp = br.readLine()) != null)
            {
                sb.append(temp);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                br.close(); // stop reading
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        jsons = sb.toString();

        // Try to parse JSON
        try
        {
            // Creating JSONObject from String
            JSONObject jsonObjMain = new JSONObject(jsons);
            // Creating JSONArray from JSONObject
            JSONArray jsonArray = jsonObjMain.getJSONArray(typeObject);

            // JSONArray has four JSONObject
            for (int i = 0; i < jsonArray.length(); i++)
            {
                // Creating JSONObject from JSONArray
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                // Getting data from individual JSONObject
                String url = jsonObj.getString("url");
                String description = jsonObj.getString("description");

                if (typeObject.equals(typeObject))
                {
                    Uri pathVideo = Uri.parse(url);
                    Video video = new Video(pathVideo, description);
                    arrayList.add(video);
                }
                else
                {
                    Image image = new Image(url, description);
                    arrayList.add(image);
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static void checkHiddenOrVisibleButton(LaunchActivity launchActivity)
    {
        String typeScreen = new SharedPreferencesManager(launchActivity).getString(Constant.SCREEN_TYPE);
        if (typeScreen.equals(Constant.SCREEN_VIDEO1_DETAIL))
        {
            launchActivity.findViewById(R.id.ibtPurchase1).setVisibility(View.GONE);
        }
        else if (typeScreen.equals(Constant.SCREEN_VIDEO2_DETAIL))
        {
            launchActivity.findViewById(R.id.ibtPurchase2).setVisibility(View.GONE);
        }
        else if (typeScreen.equals(Constant.SCREEN_VIDEO3_DETAIL))
        {
            launchActivity.findViewById(R.id.ibtPurchase3).setVisibility(View.GONE);
        }
    }
}
