package com.drunk3nm.gallery;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



public class startService extends Activity
{
    Button btnStart;

    //private StartAppAd startAppAd = new StartAppAd(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        super.onCreate(savedInstanceState);
      //  StartAppSearch.init(this);
        setContentView(R.layout.startservice);

        btnStart=(Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i=new Intent();
                i.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
                startActivity(i);

            }
        });



    }

    @Override
    public void onBackPressed() {
        //startAppAd.onBackPressed();
        super.onBackPressed();
    }
}
