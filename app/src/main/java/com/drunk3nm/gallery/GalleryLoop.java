package com.drunk3nm.gallery;




import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;


@SuppressWarnings("deprecation")
public class GalleryLoop extends Activity
{
    public SharedPreferences preferences;
    public Integer[] mThumbIds1 = {R.drawable.black, R.drawable.backa, R.drawable.backb, R.drawable.backc, R.drawable.backd,
            R.drawable.backe , R.drawable.backf, R.drawable.backg, R.drawable.backh, R.drawable.backi, R.drawable.backj,
            R.drawable.backk, R.drawable.backl, R.drawable.backm};

    // Keep all Images in array
    public Integer[] mThumbIds = {R.drawable.blacksquare, R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four,
            R.drawable.five, R.drawable.six, R.drawable.seven, R.drawable.eight,
            R.drawable.nine, R.drawable.ten, R.drawable.eleven, R.drawable.twelve, R.drawable.thirteen};
    int positions;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.galleryloop);


        Gallery mygallery=(Gallery)findViewById(R.id.mygallery);
        final ImageView imageview=(ImageView)findViewById(R.id.imageView1);
        mygallery.setSpacing(1);
        Intent check=getIntent();
        String sid=check.getStringExtra("SIDES");

            mygallery.setAdapter(new ImageAdapter(this));

        preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if(sid.contentEquals("FRONT"))
        {
            positions=preferences.getInt("frontposition", 0);
            imageview.setImageResource(mThumbIds[positions]);
        }
        else if(sid.contentEquals("LEFT"))
        {
            positions=preferences.getInt("leftposition", 0);
            imageview.setImageResource(mThumbIds[positions]);
        }
        else if(sid.contentEquals("RIGHT"))
        {
            positions=preferences.getInt("rightposition", 0);
            imageview.setImageResource(mThumbIds[positions]);
        }
        else if(sid.contentEquals("BACK"))
        {
            positions=preferences.getInt("backposition", 0);
            imageview.setImageResource(mThumbIds[positions]);
        }
        else if(sid.contentEquals("TOP"))
        {
            positions=preferences.getInt("topposition", 0);
            imageview.setImageResource(mThumbIds[positions]);
        }
        else if(sid.contentEquals("BOTTOM"))
        {
            positions=preferences.getInt("bottomposition", 0);
            imageview.setImageResource(mThumbIds[positions]);
        }
        else if(sid.contentEquals("BACKGROUND"))
        {
            int positions=preferences.getInt("backgroundposition", 0);
            imageview.setImageResource(mThumbIds[positions]);
        }
        else if(sid.contentEquals("ALLINONE"))
        {
            int posit=preferences.getInt("allinoneposition", 0);
            imageview.setImageResource(mThumbIds[posit]);
        }
        //imageview.setImageResource(mThumbIds[positions]);

        mygallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Intent i=getIntent();
                String msg=i.getStringExtra("SIDES");
                preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edit=preferences.edit();
                edit.commit();
                if(msg.contentEquals("FRONT"))
                {
                    //Toast.makeText(GalleryLoop.this, "Front Cube Image has been Changed ", Toast.LENGTH_SHORT).show();
                    // show the selected Image
                    imageview.setImageResource(mThumbIds[position]);
                    int resourceid=mThumbIds[position];
                    edit.putInt("frontposition", position);
                    edit.putInt("GImageFront", resourceid);
                    edit.putString("GfrontPath", "");
                    edit.putString("CheckAll", "false");
                    edit.commit();
                    i.putExtra("FRONT", "FRONT");
                    setResult(RESULT_OK, i);
                    finish();
                    //Toast.makeText(getApplicationContext(), resourceid, Toast.LENGTH_LONG).show();
                }
                else if(msg.contentEquals("LEFT"))
                {
                    //Toast.makeText(GalleryLoop.this, "Left Cube Image has been Changed ", Toast.LENGTH_SHORT).show();
                    // show the selected Image
                    imageview.setImageResource(mThumbIds[position]);
                    int resourceid=mThumbIds[position];
                    edit.putInt("leftposition", position);
                    edit.putInt("GImageLeft", resourceid);
                    edit.putString("GleftPath", "");
                    edit.putString("CheckAll", "false");
                    edit.commit();
                    i.putExtra("LEFT", "LEFT");
                    setResult(RESULT_OK, i);
                    finish();
                    //Toast.makeText(getApplicationContext(), resourceid, Toast.LENGTH_LONG).show();
                }
                else if(msg.contentEquals("BACK"))
                {
                    //Toast.makeText(GalleryLoop.this, "Right Cube Image has been Changed ", Toast.LENGTH_SHORT).show();
                    // show the selected Image
                    imageview.setImageResource(mThumbIds[position]);
                    int resourceid=mThumbIds[position];
                    edit.putInt("backposition", position);
                    edit.putInt("GImageBack", resourceid);
                    edit.putString("GbackPath", "");
                    edit.putString("CheckAll", "false");
                    edit.commit();
                    i.putExtra("BACK", "BACK");
                    setResult(RESULT_OK, i);
                    finish();
                    //Toast.makeText(getApplicationContext(), resourceid, Toast.LENGTH_LONG).show();
                }
                else if(msg.contentEquals("RIGHT"))
                {
                    //Toast.makeText(GalleryLoop.this, "Right Cube Image has been Changed ", Toast.LENGTH_SHORT).show();
                    // show the selected Image
                    imageview.setImageResource(mThumbIds[position]);
                    int resourceid=mThumbIds[position];
                    edit.putInt("rightposition", position);
                    edit.putInt("GImageRight", resourceid);
                    edit.putString("GrightPath", "");
                    edit.putString("CheckAll", "false");
                    edit.commit();
                    i.putExtra("RIGHT", "RIGHT");
                    setResult(RESULT_OK, i);
                    finish();
                    //Toast.makeText(getApplicationContext(), resourceid, Toast.LENGTH_LONG).show();
                }
                else if(msg.contentEquals("TOP"))
                {
                    //Toast.makeText(GalleryLoop.this, "Right Cube Image has been Changed ", Toast.LENGTH_SHORT).show();
                    // show the selected Image
                    imageview.setImageResource(mThumbIds[position]);
                    int resourceid=mThumbIds[position];
                    edit.putInt("topposition", position);
                    edit.putInt("GImageTop", resourceid);
                    edit.putString("GtopPath", "");
                    edit.putString("CheckAll", "false");
                    edit.commit();
                    i.putExtra("TOP", "TOP");
                    setResult(RESULT_OK, i);
                    finish();
                    //Toast.makeText(getApplicationContext(), resourceid, Toast.LENGTH_LONG).show();
                }
                else if(msg.contentEquals("BOTTOM"))
                {
                    //Toast.makeText(GalleryLoop.this, "Right Cube Image has been Changed ", Toast.LENGTH_SHORT).show();
                    // show the selected Image
                    imageview.setImageResource(mThumbIds[position]);
                    int resourceid=mThumbIds[position];
                    edit.putInt("bottomposition", position);
                    edit.putInt("GImageBottom", resourceid);
                    edit.putString("GbottomPath", "");
                    edit.putString("CheckAll", "false");
                    edit.commit();
                    i.putExtra("BOTTOM", "BOTTOM");
                    setResult(RESULT_OK, i);
                    finish();

                    //Toast.makeText(getApplicationContext(), resourceid, Toast.LENGTH_LONG).show();
                }
                else if(msg.contentEquals("BACKGROUND"))
                {
                    //Toast.makeText(GalleryLoop.this, "Right Cube Image has been Changed ", Toast.LENGTH_SHORT).show();
                    // show the selected Image
                    imageview.setImageResource(mThumbIds[position]);
                    int resourceid=mThumbIds1[position];
                    //Toast.makeText(getApplicationContext(), resourceid, Toast.LENGTH_LONG).show();
                    edit.putInt("backgroundposition", position);
                    edit.putInt("GImageBackground", resourceid);
                    edit.putString("GbackgroundPath", "");
                    edit.putString("CheckAll", "false");
                    edit.commit();

                    i.putExtra("BACKGROUND", "BACKGROUND");
                    setResult(RESULT_OK, i);
                    finish();

                    //Toast.makeText(getApplicationContext(), resourceid, Toast.LENGTH_LONG).show();
                }
                else if(msg.contentEquals("ALLINONE"))
                {
                    //Toast.makeText(GalleryLoop.this, "Right Cube Image has been Changed ", Toast.LENGTH_SHORT).show();
                    // show the selected Image
                    imageview.setImageResource(mThumbIds[position]);
                    int resourceid=mThumbIds[position];
                    int resourceid1 = mThumbIds1[position];
                    edit.putInt("frontposition", position);
                    edit.putInt("leftposition", position);
                    edit.putInt("backposition", position);
                    edit.putInt("rightposition", position);
                    edit.putInt("topposition", position);
                    edit.putInt("bottomposition", position);
                    edit.putInt("backgroundposition", position);
                    edit.putInt("allinoneposition", position);
                    edit.putString("CheckAll", "true");
                    edit.putInt("GImageFront", resourceid);
                    edit.putInt("GImageLeft", resourceid);
                    edit.putInt("GImageBack", resourceid);
                    edit.putInt("GImageRight", resourceid);
                    edit.putInt("GImageTop", resourceid);
                    edit.putInt("GImageBottom", resourceid);
                    edit.putInt("GImageBackground", resourceid1);
                    edit.putInt("GImageAllinone", resourceid);
                    edit.putString("GAllinonePath", "");
                    edit.commit();
                    i.putExtra("ALLINONE", "ALLINONE");
                    setResult(RESULT_OK, i);
                    finish();

                    //Toast.makeText(getApplicationContext(), resourceid, Toast.LENGTH_LONG).show();
                }
            }


        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // Intent i=new Intent(GalleryLoop.this,ChangeCubeImages.class);
       // startActivity(i);
        finish();
    }
}

