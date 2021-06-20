package com.drunk3nm.gallery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class ChangeCubeImages extends Activity
{
    CustomGridAdapter adapter;
    GridView gridView;
    public static final int REQUEST_IMAGE = 100;
    Button btnSingleImage;
    AlertDialog ImageDialog;
    String face;
    ImageView ivAll,ivBackground;
    private Uri mImageCaptureUri;
    public SharedPreferences preferences;
    private int position;
    private Bitmap frontBitmap;
    final CharSequence[] items = {" Application "," Gallery "};
  //  final CharSequence[] items = {" Application "};
    static final String[ ] GRID_DATA = new String[] {"FRONT","LEFT","BACK","RIGHT","TOP","BOTTOM"};
    public Integer[] mThumbIds = {R.drawable.black, R.drawable.backa, R.drawable.backb, R.drawable.backc, R.drawable.backd,
            R.drawable.backe , R.drawable.backf, R.drawable.backg, R.drawable.backh, R.drawable.backi, R.drawable.backj,
            R.drawable.backk, R.drawable.backl, R.drawable.backm};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridcubechange);


        //btnSingleImage=(Button)findViewById(R.id.btnSingleImage);
//        LinearLayout l=(LinearLayout)findViewById(R.id.single);
        LinearLayout ll=(LinearLayout)findViewById(R.id.singleBg);
        gridView = (GridView) findViewById(R.id.gridView1);
       // ivAll=(ImageView)findViewById(R.id.ivAll);
        ivBackground=(ImageView)findViewById(R.id.ivBackground);
        // Single Image Selector
        preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String checkall=preferences.getString("CheckAll", "false");
        int posit=preferences.getInt("allinoneposition", 0);
//        if(checkall=="false")
//        {
//            ivAll.setImageResource(R.drawable.notselected);
//        }
//        else
//        {
//            ivAll.setImageResource(mThumbIds[posit]);
//        }
         /*if(selectedImageUri.length()==0)
			{
        	 //Toast.makeText(getApplicationContext(), "No Image", Toast.LENGTH_SHORT).show();
				ivAll.setImageResource(R.drawable.notselected);
			}
			else
			{
				Uri myUri = Uri.parse(selectedImageUri);
				try{
				 ivAll.setImageURI(myUri);
				//Toast.makeText(getApplicationContext(), "Image selected", Toast.LENGTH_SHORT).show();
				}catch(Exception e){
					Toast.makeText(ChangeCubeImages.this, "Image Should be not more than 800x600", Toast.LENGTH_LONG).show();
					ivAll.setImageResource(R.drawable.notselected);
				}
			}
*/
        // Background Image Selector
        int imagebackground= preferences.getInt("GImageBackground", mThumbIds[0]);

        String selectedBackgroundImageUri=preferences.getString("GbackgroundPath", "");
        if(selectedBackgroundImageUri.length()==0)
        {
            ivBackground.setImageResource(imagebackground);

        }
        else
        {
            selectedBackgroundImageUri=preferences.getString("GbackgroundPath", "");
            Uri myUrib = Uri.parse(selectedBackgroundImageUri);
            ivBackground.setImageURI(myUrib);
        }

        // Set custom adapter (GridAdapter) to gridview
        adapter=new CustomGridAdapter(this, GRID_DATA);
        gridView.setAdapter(adapter);

         AlertDialog.Builder builder=new AlertDialog.Builder(this);
         builder.setTitle("Select Image from");
         builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int item) {

				switch(item)
				{
				case 0:

					 Toast.makeText(getApplicationContext(), face, Toast.LENGTH_LONG).show();
					 Intent i=new Intent(ChangeCubeImages.this,GalleryLoop.class);
            		 i.putExtra("SIDES", face);
            		 startActivityForResult(i, position);
            		 break;
				case 1:
					 Toast.makeText(getApplicationContext(), face, Toast.LENGTH_LONG).show();
					 Intent in=new Intent(ChangeCubeImages.this, GetImage.class);
					 in.putExtra("SIDES", face);
                    in.putExtra(GetImage.INTENT_IMAGE_PICKER_OPTION, GetImage.REQUEST_GALLERY_IMAGE);

                    // setting aspect ratio
                    in.putExtra(GetImage.INTENT_LOCK_ASPECT_RATIO, false);
                    in.putExtra(GetImage.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
                    in.putExtra(GetImage.INTENT_ASPECT_RATIO_Y, 1);
                    startActivityForResult(in, position);
					// startActivityForResult(in, position);
					 break;
				}
				ImageDialog.dismiss();
			}
		});
         ImageDialog=builder.create();

        gridView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                ChangeCubeImages.this.position=position;

                if(position==0)
                {
                    face="FRONT";
                    Toast.makeText(getApplicationContext(), face, Toast.LENGTH_LONG).show();
                    Intent in=new Intent(ChangeCubeImages.this, GetImage.class);
                    in.putExtra("SIDES", face);
                    in.putExtra(GetImage.INTENT_IMAGE_PICKER_OPTION, GetImage.REQUEST_GALLERY_IMAGE);

                    // setting aspect ratio
                    in.putExtra(GetImage.INTENT_LOCK_ASPECT_RATIO, false);
                    in.putExtra(GetImage.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
                    in.putExtra(GetImage.INTENT_ASPECT_RATIO_Y, 1);
                    startActivityForResult(in, position);
                }
                if(position==1)
                {
                    face="LEFT";
                    Toast.makeText(getApplicationContext(), face, Toast.LENGTH_LONG).show();
                    Intent in=new Intent(ChangeCubeImages.this, GetImage.class);
                    in.putExtra("SIDES", face);
                    in.putExtra(GetImage.INTENT_IMAGE_PICKER_OPTION, GetImage.REQUEST_GALLERY_IMAGE);

                    // setting aspect ratio
                    in.putExtra(GetImage.INTENT_LOCK_ASPECT_RATIO, false);
                    in.putExtra(GetImage.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
                    in.putExtra(GetImage.INTENT_ASPECT_RATIO_Y, 1);
                    startActivityForResult(in, position);
                }
                if(position==2)
                {
                    face="BACK";
                    Toast.makeText(getApplicationContext(), face, Toast.LENGTH_LONG).show();
                    Intent in=new Intent(ChangeCubeImages.this, GetImage.class);
                    in.putExtra("SIDES", face);
                    in.putExtra(GetImage.INTENT_IMAGE_PICKER_OPTION, GetImage.REQUEST_GALLERY_IMAGE);

                    // setting aspect ratio
                    in.putExtra(GetImage.INTENT_LOCK_ASPECT_RATIO, false);
                    in.putExtra(GetImage.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
                    in.putExtra(GetImage.INTENT_ASPECT_RATIO_Y, 1);
                    startActivityForResult(in, position);
                }
                if(position==3)
                {
                    face="RIGHT";
                    Toast.makeText(getApplicationContext(), face, Toast.LENGTH_LONG).show();
                    Intent in=new Intent(ChangeCubeImages.this, GetImage.class);
                    in.putExtra("SIDES", face);
                    in.putExtra(GetImage.INTENT_IMAGE_PICKER_OPTION, GetImage.REQUEST_GALLERY_IMAGE);

                    // setting aspect ratio
                    in.putExtra(GetImage.INTENT_LOCK_ASPECT_RATIO, false);
                    in.putExtra(GetImage.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
                    in.putExtra(GetImage.INTENT_ASPECT_RATIO_Y, 1);
                    startActivityForResult(in, position);
                }
                if(position==4)
                {
                    face="TOP";
                    Toast.makeText(getApplicationContext(), face, Toast.LENGTH_LONG).show();
                    Intent in=new Intent(ChangeCubeImages.this, GetImage.class);
                    in.putExtra("SIDES", face);
                    in.putExtra(GetImage.INTENT_IMAGE_PICKER_OPTION, GetImage.REQUEST_GALLERY_IMAGE);

                    // setting aspect ratio
                    in.putExtra(GetImage.INTENT_LOCK_ASPECT_RATIO, false);
                    in.putExtra(GetImage.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
                    in.putExtra(GetImage.INTENT_ASPECT_RATIO_Y, 1);
                    startActivityForResult(in, position);
                }
                if(position==5)
                {
                    face="BOTTOM";
                    Toast.makeText(getApplicationContext(), face, Toast.LENGTH_LONG).show();
                    Intent in=new Intent(ChangeCubeImages.this, GetImage.class);
                    in.putExtra("SIDES", face);
                    in.putExtra(GetImage.INTENT_IMAGE_PICKER_OPTION, GetImage.REQUEST_GALLERY_IMAGE);

                    // setting aspect ratio
                    in.putExtra(GetImage.INTENT_LOCK_ASPECT_RATIO, false);
                    in.putExtra(GetImage.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
                    in.putExtra(GetImage.INTENT_ASPECT_RATIO_Y, 1);
                    startActivityForResult(in, position);
                }
//            	 if(position==6)
//            	 {
//            		 face="BACKGROUND";
//            		 //ImageDialog.show();
//            		 Toast.makeText(getApplicationContext(), face, Toast.LENGTH_LONG).show();
//            		 Intent i=new Intent(ChangeCubeImages.this,GalleryLoop.class);
//            		 i.putExtra("SIDES", face);
//            		 startActivityForResult(i, position);
//            	 }
            }
        });

//        l.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                face="ALLINONE";
//                Toast.makeText(getApplicationContext(), face, Toast.LENGTH_LONG).show();
//                Intent i=new Intent(ChangeCubeImages.this,GalleryLoop.class);
//                i.putExtra("SIDES", face);
//                startActivityForResult(i, position);
//            }
//        });

        ll.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                face="BACKGROUND";
                //ImageDialog.show();
                Toast.makeText(getApplicationContext(), face, Toast.LENGTH_LONG).show();
                Intent i=new Intent(ChangeCubeImages.this,GalleryLoop.class);
                i.putExtra("SIDES", face);
                startActivityForResult(i, position);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
        {
            adapter=new CustomGridAdapter(this, GRID_DATA);
            gridView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            return;
        }
        if(data.getExtras() !=null){
            if(data.getExtras().containsKey("FRONT"))
            {
                Toast.makeText(getApplicationContext(), "FRONT Image of Cube has Changed", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
            }
            else if(data.getExtras().containsKey("LEFT"))
            {
                Toast.makeText(getApplicationContext(), "LEFT Image of Cube has Changed", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
            }
            else if(data.getExtras().containsKey("BACK"))
            {
                Toast.makeText(getApplicationContext(), "BACK Image of Cube has Changed", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
            }
            else if(data.getExtras().containsKey("RIGHT"))
            {
                Toast.makeText(getApplicationContext(), "RIGHT Image of Cube has Changed", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
            }
            else if(data.getExtras().containsKey("TOP"))
            {
                Toast.makeText(getApplicationContext(), "TOP Image of Cube has Changed", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
            }
            else if(data.getExtras().containsKey("BOTTOM"))
            {
                Toast.makeText(getApplicationContext(), "BOTTOM Image of Cube has Changed", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
            }
            else if(data.getExtras().containsKey("BACKGROUND"))
            {
                Toast.makeText(getApplicationContext(), "BACKGROUND Image has Changed", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
            }
            else if(data.getExtras().containsKey("ALLINONE"))
            {
                Toast.makeText(getApplicationContext(), "All Image has Changed", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
            }


		/*if(requestCode==4)
		{
			mImageCaptureUri = data.getData();
			face="ALLINONE";
			doCrop();
		}*/

	    /*            String selectedImage=root+"/glw/glw/allinone.png";
	                 //Toast.makeText(this, selectedImagePath, Toast.LENGTH_LONG).show();
	                 preferences = PreferenceManager.getDefaultSharedPreferences(this);
	                 edit.putString("GAllPath", selectedImage);
	                 edit.putString("GfrontPath", selectedImage);
	                 edit.putString("GleftPath", selectedImage);
	                 edit.putString("GrightPath", selectedImage);
	                 edit.putString("GbackPath", selectedImage);
	                 edit.putString("GtopPath", selectedImage);
	                 edit.putString("GbottomPath", selectedImage);
	                 edit.commit();
	                 String selectedImageUri=preferences.getString("GAllPath", "");

	                 Intent intent = getIntent();
	                 overridePendingTransition(0, 0);
	                 intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	                 finish();
	                 overridePendingTransition(0, 0);
	                 startActivity(intent);
			}*/

        }
		/*else
		{
			Intent intent = getIntent();
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
		}*/

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
    }


}
