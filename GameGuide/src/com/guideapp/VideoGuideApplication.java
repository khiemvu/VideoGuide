package com.guideapp;

import android.app.Application;
import android.content.Context;
import net.robotmedia.billing.BillingController;

/**
 * User: khiemvx
 * Date: 7/28/14
 */
public class VideoGuideApplication extends Application
{
    public static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();

        BillingController.setDebug(true);
        BillingController.setConfiguration(new BillingController.IConfiguration()
        {

            public byte[] getObfuscationSalt()
            {
                return new byte[]{41, -90, -116, -41, 66, -53, 122, -110, -127, -96, -88, 77, 127, 115, 1, 73, 57, 110, 48, -116};
            }

            public String getPublicKey()
            {
                return "your public key here";
            }
        });
        context = getApplicationContext();
    }
}
