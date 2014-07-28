package net.robotmedia.billing.example.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import net.robotmedia.billing.BillingController;
import net.robotmedia.billing.BillingRequest;
import net.robotmedia.billing.dungeons.redux.R;
import net.robotmedia.billing.helper.AbstractBillingObserver;
import net.robotmedia.billing.model.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Khiemvx
 * Date: 3/4/14
 */
public class MainActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);
    }
}