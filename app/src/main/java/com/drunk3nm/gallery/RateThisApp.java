package com.drunk3nm.gallery;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class RateThisApp extends Activity
{
    Button btnRateApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ratemyapp);
        btnRateApp=(Button)findViewById(R.id.btnRateApp);

        btnRateApp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //Try Google play
                intent.setData(Uri.parse("market://details?id=com.drunk3nm.gallery"));
                if (RateThisApps(intent) == false) {
                    //Market (Google play) app seems not installed, let's try to open a webbrowser
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.drunk3nm.gallery"));
                    if (RateThisApps(intent) == false) {
                        //Well if this also fails, we have run out of options, inform the user.
                        Toast.makeText(RateThisApp.this, "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private boolean RateThisApps(Intent aIntent) {
        try
        {
            startActivity(aIntent);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }

}
