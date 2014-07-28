package com.guideapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.guideapp.R;
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
        btPurchase1.setOnClickListener(onclickListener);
        btPurchase2.setOnClickListener(onclickListener);
        btPurchase3.setOnClickListener(onclickListener);
        BillingController.registerObserver(mBillingObserver);
        BillingController.checkBillingSupported(this);
        BillingController.checkSubscriptionSupported(this);
        updateOwnedItems();
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
        if (transactions.size()>0)
        {
            Intent intent = new Intent(LaunchActivity.this, ImageActivity.class);
            startActivity(intent);
            finish();
        }


    }

    private View.OnClickListener onclickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
             BillingController.requestPurchase(LaunchActivity.this, "android.test.purchased", true /* confirm */, null);
        }
    };
}
