package com.drunk3nm.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class CustomGridAdapter extends BaseAdapter {
    public Integer[] mThumbIds = {R.drawable.black, R.drawable.backa, R.drawable.backb, R.drawable.backc, R.drawable.backd,
            R.drawable.backe , R.drawable.backf, R.drawable.backg, R.drawable.backh, R.drawable.backi, R.drawable.backj,
            R.drawable.backk, R.drawable.backl, R.drawable.backm};

    Bitmap bitmap = null;
    private Context context;
    private final String[] gridValues;
    SharedPreferences preferences;

    //Constructor to initialize values
    public CustomGridAdapter(Context context, String[ ] gridValues) {

        this.context        = context;
        this.gridValues     = gridValues;
    }

    @Override
    public int getCount() {

        // Number of times getView method call depends upon gridValues.length
        return gridValues.length;
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }


    // Number of times getView method call depends upon gridValues.length

    public View getView(int position, View convertView, ViewGroup parent) {

        // LayoutInflator to call external grid_item.xml file

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from grid_item.xml ( Defined Below )

            gridView = inflater.inflate( R.layout.grid_item , null);

            // set value into textview

            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_label);

            textView.setText(gridValues[position]);

            // set image based on selected text

            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            String arrLabel = gridValues[ position ];

            if (arrLabel.equals("TOP")) {
                preferences = PreferenceManager.getDefaultSharedPreferences(context);
                int imagetop= preferences.getInt("GImageTop", mThumbIds[1]);
                String selectedImageUri=preferences.getString("GtopPath", "");
                if(selectedImageUri.length()==0)
                {
                    imageView.setImageResource(imagetop);

                }
                else
                {
                    selectedImageUri=preferences.getString("GtopPath", "");
                    Uri myUri = Uri.parse(selectedImageUri);
                    try{
                        imageView.setImageURI(myUri);
                    }catch(Exception e){
                        Toast.makeText(context, "Image Should be not more than 800x600", Toast.LENGTH_LONG).show();
                        imageView.setImageResource(imagetop);
                    }
                }

            } else if (arrLabel.equals("LEFT")) {
                preferences = PreferenceManager.getDefaultSharedPreferences(context);
                int imageleft= preferences.getInt("GImageLeft", mThumbIds[1]);

                String selectedImageUri=preferences.getString("GleftPath", "");
                if(selectedImageUri.length()==0)
                {
                    imageView.setImageResource(imageleft);

                }
                else
                {
                    selectedImageUri=preferences.getString("GleftPath", "");
                    Uri myUri = Uri.parse(selectedImageUri);
                    imageView.setImageURI(myUri);
                }

            } else if (arrLabel.equals("RIGHT")) {
                preferences = PreferenceManager.getDefaultSharedPreferences(context);
                int imageright= preferences.getInt("GImageRight", mThumbIds[1]);
                String selectedImageUri=preferences.getString("GrightPath","");
                if(selectedImageUri.length()==0)
                {
                    imageView.setImageResource(imageright);
                }
                else
                {
                    selectedImageUri=preferences.getString("GrightPath", "");
                    Uri myUri = Uri.parse(selectedImageUri);
                    imageView.setImageURI(myUri);
                }


            }  else if (arrLabel.equals("FRONT")) {

                preferences = PreferenceManager.getDefaultSharedPreferences(context);
                int imagefront= preferences.getInt("GImageFront", mThumbIds[1]);

                String selectedImageUri=preferences.getString("GfrontPath", "");
                if(selectedImageUri.length()==0)
                {
                    imageView.setImageResource(imagefront);

                }
                else
                {
                    selectedImageUri=preferences.getString("GfrontPath", "");
                    Uri myUri = Uri.parse(selectedImageUri);
                    imageView.setImageURI(myUri);


                }
            } else if (arrLabel.equals("BACK")) {
                preferences = PreferenceManager.getDefaultSharedPreferences(context);
                int imageback= preferences.getInt("GImageBack", mThumbIds[1]);

                String selectedImageUri=preferences.getString("GbackPath", "");
                if(selectedImageUri.length()==0)
                {
                    imageView.setImageResource(imageback);

                }
                else
                {
                    selectedImageUri=preferences.getString("GbackPath", "");
                    Uri myUri = Uri.parse(selectedImageUri);
                    imageView.setImageURI(myUri);
                }
            }else if(arrLabel.equals("BOTTOM"))
            {
                preferences = PreferenceManager.getDefaultSharedPreferences(context);
                int imagebottom= preferences.getInt("GImageBottom", mThumbIds[1]);

                String selectedImageUri=preferences.getString("GbottomPath", "");
                if(selectedImageUri.length()==0)
                {
                    imageView.setImageResource(imagebottom);

                }
                else
                {
                    selectedImageUri=preferences.getString("GbottomPath", "");
                    Uri myUri = Uri.parse(selectedImageUri);
                    imageView.setImageURI(myUri);
                }
            }
            else if(arrLabel.equals("BACKGROUND"))
            {
                preferences = PreferenceManager.getDefaultSharedPreferences(context);
                int imagebackground= preferences.getInt("GImageBackground", mThumbIds[0]);

                String selectedImageUri=preferences.getString("GbackgroundPath", "");
                if(selectedImageUri.length()==0)
                {
                    imageView.setImageResource(imagebackground);

                }
                else
                {
                    selectedImageUri=preferences.getString("GbackgroundPath", "");
                    Uri myUri = Uri.parse(selectedImageUri);
                    imageView.setImageURI(myUri);
                }
            }
        }
        else {

            gridView = (View) convertView;
        }

        return gridView;
    }

}
