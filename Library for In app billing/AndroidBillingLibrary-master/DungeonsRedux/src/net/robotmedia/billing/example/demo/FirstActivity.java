package net.robotmedia.billing.example.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import net.robotmedia.billing.dungeons.redux.R;

import java.util.ArrayList;

/**
 * User: Khiemvx
 * Date: 3/4/14
 */
public class FirstActivity extends Activity
{
    public static ArrayList<String> owner = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);

        findViewById(R.id.bt1).setOnClickListener(onclickListener);
        findViewById(R.id.bt2).setOnClickListener(onclickListener);
        findViewById(R.id.bt3).setOnClickListener(onclickListener);
    }

    private View.OnClickListener onclickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.bt1:
                case R.id.bt2:
                    if(owner.size() == 0){
                        Intent intent = new Intent(FirstActivity.this, FlashActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Intent intent  = new Intent(FirstActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    break;
//                case R.id.bt3:
//                    Intent intent = new Intent(FirstActivity.this, FlashActivity.class);
//                    startActivity(intent);
//                    break;
            }
        }
    };
}
