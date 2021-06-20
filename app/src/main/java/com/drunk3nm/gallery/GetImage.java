package com.drunk3nm.gallery;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class GetImage extends Activity
{
    Context context;
    FileOutputStream outputStream;
    private ImageView resultIv;
    private Boolean storagePermission;
    private Button selectBtn;
    public SharedPreferences preferences;

    public static final String INTENT_IMAGE_PICKER_OPTION = "image_picker_option";
    public static final String INTENT_ASPECT_RATIO_X = "aspect_ratio_x";
    public static final String INTENT_ASPECT_RATIO_Y = "aspect_ratio_Y";
    public static final String INTENT_LOCK_ASPECT_RATIO = "lock_aspect_ratio";

    public static final int REQUEST_GALLERY_IMAGE = 1;

    private boolean setBitmapMaxWidthHeight = false;
    private int  bitmapMaxWidth = 1000, bitmapMaxHeight = 1000;
    private int IMAGE_COMPRESSION = 80;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);



        resultIv = (ImageView) findViewById(R.id.resultIv);
        selectBtn = (Button) findViewById(R.id.btnSelect);
        storagePermission = isStoragePermissionGranted();
        Intent pictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pictureIntent.setType("image/*");  // 1
        pictureIntent.addCategory(Intent.CATEGORY_OPENABLE);  // 2
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String[] mimeTypes = new String[]{"image/jpeg", "image/png"};  // 3
            pictureIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        }
        startActivityForResult(Intent.createChooser(pictureIntent,"Select Picture"), REQUEST_GALLERY_IMAGE);

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storagePermission = isStoragePermissionGranted();
                Intent pictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
                pictureIntent.setType("image/*");  // 1
                pictureIntent.addCategory(Intent.CATEGORY_OPENABLE);  // 2
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    String[] mimeTypes = new String[]{"image/jpeg", "image/png"};  // 3
                    pictureIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                }
                startActivityForResult(Intent.createChooser(pictureIntent,"Select Picture"), REQUEST_GALLERY_IMAGE);

            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_IMAGE) {

                Intent i=getIntent();
                String msg=i.getStringExtra("SIDES");
                preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                Editor edit=preferences.edit();
                edit.commit();
                Uri imageUri = data.getData();
                cropImage(imageUri);
            }else{
                handleUCropResult(data);
            }
        }
    }




    private void cropImage(Uri sourceUri) {
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), queryName(getContentResolver(), sourceUri)));
        UCrop.Options options = new UCrop.Options();
        options.withAspectRatio(1, 1);
        options.setCompressionQuality(IMAGE_COMPRESSION);
        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.colorPrimary));

        if (setBitmapMaxWidthHeight)
            options.withMaxResultSize(bitmapMaxWidth, bitmapMaxHeight);
        Uri myUri = Uri.fromFile(new File(getExternalCacheDir(), "gfront.jpeg"));
        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(this);

    }

    private void handleUCropResult(Intent data) {
        if (data == null) {
            setResultCancelled();
            return;
        }
        final Uri resultUri = UCrop.getOutput(data);
        setResultOk(resultUri);
    }

    private void setResultOk(Uri imagePaths) {

        resultIv.setImageURI(Uri.parse(imagePaths.toString()));
        BitmapDrawable drawable = (BitmapDrawable) resultIv.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        File filePaths = Environment.getExternalStorageDirectory();
        File dir = new File(filePaths.getAbsoluteFile()+"/3dGallery/");
        dir.mkdir();
        String fileName = "";
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Intent i=getIntent();
        String msg=i.getStringExtra("SIDES");
        preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Editor edit=preferences.edit();
        edit.commit();
        if(msg.contentEquals("FRONT")) {
            fileName = "gfrontImage";
        }else if(msg.contentEquals("LEFT")){
            fileName = "gleftImage";
        }else if(msg.contentEquals("BACK")){
            fileName = "gbackImage";
        }else if(msg.contentEquals("RIGHT")){
            fileName = "grightImage";
        }else if(msg.contentEquals("TOP")){
            fileName = "gtopImage";
        }else if(msg.contentEquals("BOTTOM")){
            fileName = "gbottomImage";
        }

        File file= new File(dir,fileName+".jpg");
        try{
            outputStream = new FileOutputStream(file);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        Toast.makeText(getApplicationContext(),"Image Saved to Internal",Toast.LENGTH_SHORT);
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(msg.contentEquals("FRONT")) {
            File filePath = Environment.getExternalStorageDirectory();
            String imagePath = filePath.getAbsoluteFile()+"/3dGallery/gfrontImage.jpg";
            edit.putString("GfrontPath", imagePath.toString());
            edit.commit();
            i.putExtra("FRONT", "front");
            setResult(RESULT_OK, i);
            finish();
        }else if(msg.contentEquals("LEFT"))
        {
            File filePath = Environment.getExternalStorageDirectory();
            String imagePath = filePath.getAbsoluteFile()+"/3dGallery/gleftImage.jpg";
            edit.putString("GleftPath", imagePath.toString());
            edit.commit();
            i.putExtra("LEFT", "left");
            setResult(RESULT_OK, i);
            finish();
        }
        else if(msg.contentEquals("BACK"))
        {
            File filePath = Environment.getExternalStorageDirectory();
            String imagePath = filePath.getAbsoluteFile()+"/3dGallery/gbackImage.jpg";
            edit.putString("GbackPath", imagePath.toString());
            edit.commit();
            i.putExtra("BACK", "back");
            setResult(RESULT_OK, i);
            finish();
        }
        else if(msg.contentEquals("RIGHT"))
        {
            File filePath = Environment.getExternalStorageDirectory();
            String imagePath = filePath.getAbsoluteFile()+"/3dGallery/grightImage.jpg";
            edit.putString("GrightPath", imagePath.toString());
            edit.commit();
            i.putExtra("RIGHT", "right");
            setResult(RESULT_OK, i);
            finish();
        }
        else if(msg.contentEquals("TOP"))
        {
            File filePath = Environment.getExternalStorageDirectory();
            String imagePath = filePath.getAbsoluteFile()+"/3dGallery/gtopImage.jpg";
            edit.putString("GtopPath", imagePath.toString());
            edit.commit();
            i.putExtra("TOP", "top");
            setResult(RESULT_OK, i);
            finish();
        }
        else if(msg.contentEquals("BOTTOM"))
        {
            File filePath = Environment.getExternalStorageDirectory();
            String imagePath = filePath.getAbsoluteFile()+"/3dGallery/gbottomImage.jpg";
            edit.putString("GbottomPath", imagePath.toString());
            edit.commit();
            i.putExtra("BOTTOM", "bottom");
            setResult(RESULT_OK, i);
            finish();
        }



    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Storage permission : ","Permission is granted");
                return true;
            } else {

                Log.v("Storage Permission : ","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Storage Permission : ","Permission is granted");
            return true;
        }
    }

    private void setResultCancelled() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }




    private static String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }




    @Override
    public void onBackPressed() {

        // TODO Auto-generated method stub
        super.onBackPressed();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Intent i=getIntent();
        String msg=i.getStringExtra("SIDES");
        preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Editor edit=preferences.edit();
        edit.commit();
        if(msg.contentEquals("FRONT")) {
            File filePath = Environment.getExternalStorageDirectory();
            String imagePath = filePath.getAbsoluteFile()+"/3dGallery/gfrontImage.jpg";
            edit.putString("GfrontPath", imagePath.toString());
            edit.commit();
            i.putExtra("FRONT", "front");
            setResult(RESULT_OK, i);
            finish();
        }else if(msg.contentEquals("LEFT"))
        {
            File filePath = Environment.getExternalStorageDirectory();
            String imagePath = filePath.getAbsoluteFile()+"/3dGallery/gleftImage.jpg";
            edit.putString("GleftPath", imagePath.toString());
            edit.commit();
            i.putExtra("LEFT", "left");
            setResult(RESULT_OK, i);
            finish();
        }
        else if(msg.contentEquals("BACK"))
        {
            File filePath = Environment.getExternalStorageDirectory();
            String imagePath = filePath.getAbsoluteFile()+"/3dGallery/gbackImage.jpg";
            edit.putString("GbackPath", imagePath.toString());
            edit.commit();
            i.putExtra("BACK", "back");
            setResult(RESULT_OK, i);
            finish();
        }
        else if(msg.contentEquals("RIGHT"))
        {
            File filePath = Environment.getExternalStorageDirectory();
            String imagePath = filePath.getAbsoluteFile()+"/3dGallery/grightImage.jpg";
            edit.putString("GrightPath", imagePath.toString());
            edit.commit();
            i.putExtra("RIGHT", "right");
            setResult(RESULT_OK, i);
            finish();
        }
        else if(msg.contentEquals("TOP"))
        {
            File filePath = Environment.getExternalStorageDirectory();
            String imagePath = filePath.getAbsoluteFile()+"/3dGallery/gtopImage.jpg";
            edit.putString("GtopPath", imagePath.toString());
            edit.commit();
            i.putExtra("TOP", "top");
            setResult(RESULT_OK, i);
            finish();
        }
        else if(msg.contentEquals("BOTTOM"))
        {
            File filePath = Environment.getExternalStorageDirectory();
            String imagePath = filePath.getAbsoluteFile()+"/3dGallery/gbottomImage.jpg";
            edit.putString("GbottomPath", imagePath.toString());
            edit.commit();
            i.putExtra("BOTTOM", "bottom");
            setResult(RESULT_OK, i);
            finish();
        }

    }

}
