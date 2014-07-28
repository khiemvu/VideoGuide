package net.robotmedia.billing.example.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
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
public class FlashActivity extends Activity
{
    private static final String TAG = "Dungeons";

    private TextView tv;

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
            tv.setEnabled(true);
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
                FlashActivity.this.onBillingChecked(supported);
            }

            public void onPurchaseStateChanged(String itemId, Transaction.PurchaseState state)
            {
                FlashActivity.this.onPurchaseStateChanged(itemId, state);
            }

            public void onRequestPurchaseResponse(String itemId, BillingRequest.ResponseCode response)
            {
                FlashActivity.this.onRequestPurchaseResponse(itemId, response);
            }

            public void onSubscriptionChecked(boolean supported)
            {
                FlashActivity.this.onSubscriptionChecked(supported);
            }

        };

        setContentView(R.layout.confirm);

        setupWidgets();
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
            Toast.makeText(this, R.string.restoring_transactions, Toast.LENGTH_LONG).show();
        }
    }

    private void setupWidgets()
    {
        tv = (TextView) findViewById(R.id.textView);
        tv.setEnabled(false);
        tv.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                BillingController.requestPurchase(FlashActivity.this, "android.test.purchased", true /* confirm */, null);
            }
        });

    }

    private void updateOwnedItems()
    {
        List<Transaction> transactions = BillingController.getTransactions(this);

        for (Transaction t : transactions)
        {
            if (t.purchaseState == Transaction.PurchaseState.PURCHASED)
            {
                FirstActivity.owner.add(t.productId);
            }
        }

        Intent intent = new Intent(FlashActivity.this, MainActivity.class);
        startActivity(intent);

    }
}