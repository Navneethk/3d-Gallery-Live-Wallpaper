package com.drunk3nm.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

/**
 * Settings for the wallpaper
 *
 * TODO These preferences seem to have no effect
 *
 *
 *
 */
public class Michael_Setting_Activity extends PreferenceActivity implements OnPreferenceChangeListener{
    SharedPreferences preferences;
    String pref;
    ListPreference mCubeSize;
    ListPreference mRotationDirection;
    ListPreference mCubePosition;
    CheckBoxPreference mCheckTouch,mCheckCube;
    //CheckBoxPreference mTransparentCube;


    private static final String ROTATION_DIRECTION="gpref_rotation_direction";
    private static final String CUBE_SIZE = "gpref_cube_size";
    private static final String CHECK_TOUCH="gpref_touch_cube";
    private static final String CUBE_DISABLED="pref_disable_cube";
    private static final String CUBE_POSITION="gpref_position_cube";
    //private static final String TRANSPARENT_CUBE="pref_transparent_cube";
    Context mcontext;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        getPreferenceManager().setSharedPreferencesName(MichaelWallpaperService.PREFERENCES);
        addPreferencesFromResource(R.xml.michaelpreferences);


        mcontext=this.getApplicationContext();
        PreferenceScreen prefs = getPreferenceScreen();
        mCubeSize=(ListPreference)prefs.findPreference(CUBE_SIZE);
        mRotationDirection=(ListPreference)prefs.findPreference(ROTATION_DIRECTION);
        mCheckTouch=(CheckBoxPreference)prefs.findPreference(CHECK_TOUCH);
        mCubePosition=(ListPreference)prefs.findPreference(CUBE_POSITION);
        mCheckCube=(CheckBoxPreference)prefs.findPreference(CUBE_DISABLED);
        //	mTransparentCube=(CheckBoxPreference)prefs.findPreference(TRANSPARENT_CUBE);
        preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int cubep=preferences.getInt("GCubePosition", 5);
        int cubesiz=preferences.getInt("GCubeSize", 6);
        mCubeSize.setValueIndex(cubesiz-4);
        if(cubep!=5)
        {
            mCubeSize.setValueIndex(0);
        }
        else
        {
            mCubePosition.setValueIndex(4);
        }
        mCubePosition.setOnPreferenceChangeListener(this);
        mCubeSize.setOnPreferenceChangeListener(this);
        mRotationDirection.setOnPreferenceChangeListener(this);
        mCheckTouch.setOnPreferenceChangeListener(this);
        mCheckCube.setOnPreferenceChangeListener(this);
        //mTransparentCube.setOnPreferenceChangeListener(this);
    }
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if(preference==mCubeSize)
        {
            int valsize=Integer.valueOf((String)newValue);
			   /*Settings.System.putInt(getContentResolver(), "CubeSize",
	                    valsize);*/
            preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            int cubeposi=preferences.getInt("GCubePosition", 5);
            if(cubeposi!=5)
            {
                preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edit=preferences.edit();
                edit.putInt("GCubePosition", 5);
                edit.putInt("GCubeSize", valsize);
                edit.commit();
            }
            else
            {
                preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edit=preferences.edit();
                edit.putInt("GCubeSize", valsize);
                edit.commit();
                return true;
            }

        }
        if(preference==mRotationDirection)
        {
            int rotdic=Integer.valueOf((String)newValue);
            preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Editor edit=preferences.edit();
            edit.putInt("GDirectionRotation", rotdic);
            edit.commit();
            return true;
        }
        if(preference==mCheckTouch)
        {
            Boolean checktouch=Boolean.valueOf((Boolean) newValue);
            preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Editor edit=preferences.edit();
            edit.putBoolean("GCheckTouch", checktouch);
            //Toast.makeText(Michael_Setting_Activity.this, "Check value is:"+checktouch, Toast.LENGTH_LONG).show();
            edit.commit();
            return true;
        }
        if(preference==mCheckCube)
        {
            Boolean checkcube=Boolean.valueOf((Boolean) newValue);
            preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Editor edit=preferences.edit();
            edit.putBoolean("CheckCube", checkcube);
            edit.commit();
            return true;
        }
        if(preference==mCubePosition)
        {
            int cubepos=Integer.valueOf((String)newValue);

            preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Editor edit=preferences.edit();
            edit.putInt("GCubePosition", cubepos);
            edit.commit();
            return true;
        }
//		if(preference==mTransparentCube)
//		{
//			Boolean trancube=Boolean.valueOf((Boolean) newValue);
//			preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//			Editor edit=preferences.edit();
//			edit.putBoolean("TranCube", trancube);
//			edit.commit();
//			return true;
//		}

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}

