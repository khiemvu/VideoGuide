package com.guideapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.guideapp.R;
import com.guideapp.utils.Constant;
import com.guideapp.utils.SharedPreferencesManager;
import net.robotmedia.billing.BillingController;
import net.robotmedia.billing.BillingRequest;
import net.robotmedia.billing.helper.AbstractBillingObserver;
import net.robotmedia.billing.model.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * User: khiemvx
 * Date: 7/28/14
 */
public class LaunchActivity extends Activity
{
    public static List<String> owner = new ArrayList<String>();
    private static final String TAG = "LaunchActivity";

    private Button btPurchase1, btPurchase2, btPurchase3;
    private LinearLayout llListImage;
    private RelativeLayout rlListVideo1, rlListVideo2, rlListVideo3;

    private static final int DIALOG_BILLING_NOT_SUPPORTED_ID = 2;

    private AbstractBillingObserver mBillingObserver;

    private Dialog createDialog(int titleId, int messageId)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titleId).setIcon(android.R.drawable.stat_sys_warning).setMessage(messageId).setCancelable(
                false).setPositiveButton(android.R.string.ok, null);
        return builder.create();
    }

    public void onBillingChecked(boolean supported)
    {
        if (supported)
        {
            restoreTransactions();
        }
        else
        {
            showDialog(DIALOG_BILLING_NOT_SUPPORTED_ID);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBillingObserver = new AbstractBillingObserver(this)
        {

            public void onBillingChecked(boolean supported)
            {
                LaunchActivity.this.onBillingChecked(supported);
            }

            public void onPurchaseStateChanged(String itemId, Transaction.PurchaseState state)
            {
                LaunchActivity.this.onPurchaseStateChanged(itemId, state);
            }

            public void onRequestPurchaseResponse(String itemId, BillingRequest.ResponseCode response)
            {
                LaunchActivity.this.onRequestPurchaseResponse(itemId, response);
            }

            public void onSubscriptionChecked(boolean supported)
            {
                LaunchActivity.this.onSubscriptionChecked(supported);
            }

        };

        setContentView(R.layout.activity_launch);
        btPurchase1 = (Button) findViewById(R.id.ibtPurchase1);
        btPurchase2 = (Button) findViewById(R.id.ibtPurchase2);
        btPurchase3 = (Button) findViewById(R.id.ibtPurchase3);
        llListImage = (LinearLayout) findViewById(R.id.llListImage);
        rlListVideo1 = (RelativeLayout) findViewById(R.id.llListVideo1);
        rlListVideo2 = (RelativeLayout) findViewById(R.id.llListVideo2);
        rlListVideo3 = (RelativeLayout) findViewById(R.id.llListVideo3);
        btPurchase1.setOnClickListener(onclickListener);
        btPurchase2.setOnClickListener(onclickListener);
        btPurchase3.setOnClickListener(onclickListener);
        rlListVideo1.setOnClickListener(onclickListener);
        rlListVideo2.setOnClickListener(onclickListener);
        rlListVideo3.setOnClickListener(onclickListener);
        llListImage.setOnClickListener(onclickListener);
        BillingController.registerObserver(mBillingObserver);
        BillingController.checkBillingSupported(this);
        BillingController.checkSubscriptionSupported(this);
//        Utils.checkHiddenOrVisibleButton(LaunchActivity.this);
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case DIALOG_BILLING_NOT_SUPPORTED_ID:
                return createDialog(R.string.billing_not_supported_title, R.string.billing_not_supported_message);
            default:
                return null;
        }
    }

    @Override
    protected void onDestroy()
    {
        BillingController.unregisterObserver(mBillingObserver);
        super.onDestroy();
    }

    public void onPurchaseStateChanged(String itemId, Transaction.PurchaseState state)
    {
        Log.i(TAG, "onPurchaseStateChanged() itemId: " + itemId);
        updateOwnedItems();
    }

    public void onRequestPurchaseResponse(String itemId, BillingRequest.ResponseCode response)
    {
    }

    public void onSubscriptionChecked(boolean supported)
    {

    }

    /**
     * Restores previous transactions, if any. This happens if the application
     * has just been installed or the user wiped data. We do not want to do this
     * on every startup, rather, we want to do only when the database needs to
     * be initialized.
     */
    private void restoreTransactions()
    {
        if (!mBillingObserver.isTransactionsRestored())
        {
            BillingController.restoreTransactions(this);
//            Toast.makeText(this, R.string.restoring_transactions, Toast.LENGTH_LONG).show();
        }
    }


    private void updateOwnedItems()
    {
        List<Transaction> transactions = BillingController.getTransactions(this);

        for (Transaction t : transactions)
        {
            if (t.purchaseState == Transaction.PurchaseState.PURCHASED)
            {
                LaunchActivity.owner.add(t.productId);
            }
        }
        String typeScreen = new SharedPreferencesManager(LaunchActivity.this).getString(Constant.SCREEN_TYPE);
        if (typeScreen.equals(Constant.SCREEN_VIDEO1_DETAIL) && transactions.size() > 0)
        {
            Intent intent = new Intent(LaunchActivity.this, VideoViewActivity.class);
            intent.putExtra(Constant.SCREEN_TYPE, "video");
            startActivity(intent);
            finish();
        }
        else if (typeScreen.equals(Constant.SCREEN_VIDEO2_DETAIL) && transactions.size() > 0)
        {
            Intent intent = new Intent(LaunchActivity.this, VideoViewActivity.class);
            intent.putExtra(Constant.SCREEN_TYPE, "video2");
            startActivity(intent);
            finish();
        }
        else if (typeScreen.equals(Constant.SCREEN_VIDEO3_DETAIL) && transactions.size() > 0)
        {
            Intent intent = new Intent(LaunchActivity.this, VideoViewActivity.class);
            intent.putExtra(Constant.SCREEN_TYPE, "video3");
            startActivity(intent);
            finish();
        }
    }

    private View.OnClickListener onclickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.ibtPurchase1:
                    new SharedPreferencesManager(LaunchActivity.this).clearCache();
                    new SharedPreferencesManager(LaunchActivity.this)
                            .setValue(Constant.SCREEN_TYPE, Constant.SCREEN_VIDEO1_DETAIL);
                    BillingController.requestPurchase(LaunchActivity.this, "android.test.purchased", true /* confirm */, null);
                    break;
                case R.id.ibtPurchase2:
                    new SharedPreferencesManager(LaunchActivity.this).clearCache();
                    new SharedPreferencesManager(LaunchActivity.this)
                            .setValue(Constant.SCREEN_TYPE, Constant.SCREEN_VIDEO2_DETAIL);
                    BillingController.requestPurchase(LaunchActivity.this, "android.test.purchased", true /* confirm */, null);
                    break;
                case R.id.ibtPurchase3:
                    new SharedPreferencesManager(LaunchActivity.this).clearCache();
                    new SharedPreferencesManager(LaunchActivity.this)
                            .setValue(Constant.SCREEN_TYPE, Constant.SCREEN_VIDEO3_DETAIL);
                    BillingController.requestPurchase(LaunchActivity.this, "android.test.purchased", true /* confirm */, null);
                    break;
                case R.id.llListImage:
                    Intent intent = new Intent(LaunchActivity.this, ImageActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.llListVideo1:
                    new SharedPreferencesManager(LaunchActivity.this).clearCache();
                    new SharedPreferencesManager(LaunchActivity.this)
                            .setValue(Constant.SCREEN_TYPE, Constant.SCREEN_VIDEO1_DETAIL);
                    updateOwnedItems();
                    break;
                case R.id.llListVideo2:
                    new SharedPreferencesManager(LaunchActivity.this).clearCache();
                    new SharedPreferencesManager(LaunchActivity.this)
                            .setValue(Constant.SCREEN_TYPE, Constant.SCREEN_VIDEO2_DETAIL);
                    updateOwnedItems();
                    break;
                case R.id.llListVideo3:
                    new SharedPreferencesManager(LaunchActivity.this).clearCache();
                    new SharedPreferencesManager(LaunchActivity.this)
                            .setValue(Constant.SCREEN_TYPE, Constant.SCREEN_VIDEO3_DETAIL);
                    updateOwnedItems();
                    break;
            }
        }
    };

    @Override
    protected void onResume()
    {
        super.onResume();
        new SharedPreferencesManager(LaunchActivity.this).clearCache();
    }
}
