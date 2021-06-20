package com.drunk3nm.gallery;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;



public class SpeedRotation extends Activity
{

    public SharedPreferences preferences;
    SeekBar seekbar;
    TextView value;
    float mprogress;
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.seekbarrotation);

        value = (TextView) findViewById(R.id.textview);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        float speedNum=preferences.getFloat("GRotationSpeed", 0.5f);
        int speedN=(int)speedNum;
        seekbar.setProgress(speedN);
        value.setText("Rotation speed set to "+speedN);
        seekbar.setOnSeekBarChangeListener( new OnSeekBarChangeListener()
        {
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser)
            {
                // TODO Auto-generated method stub
                value.setText("Rotation speed is "+progress);
                mprogress=progress*0.1f;
                preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edit=preferences.edit();
                edit.putFloat("GRotationSpeed", mprogress);
                edit.commit();
                //Toast.makeText(getApplicationContext(), "preference saved "+mprogress, Toast.LENGTH_LONG).show();
            }

            public void onStartTrackingTouch(SeekBar seekBar)
            {
// TODO Auto-generated method stub
            }
            public void onStopTrackingTouch(SeekBar seekBar)
            {
// TODO Auto-generated method stub
                preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edit=preferences.edit();
                edit.putFloat("GRotationSpeed", seekBar.getProgress());
                edit.commit();
            }
        });


    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        super.onBackPressed();
    }

}
