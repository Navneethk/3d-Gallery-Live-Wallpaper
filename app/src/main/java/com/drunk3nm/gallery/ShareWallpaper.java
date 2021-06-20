package com.drunk3nm.gallery;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ShareWallpaper extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.drunk3nm.gallery");

        intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this 3D Heart Live Wallpaper!");

        startActivity(Intent.createChooser(intent, "Share"));
        this.finish();
    }

}
