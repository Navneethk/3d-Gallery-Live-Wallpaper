package com.drunk3nm.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.preference.PreferenceManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Dummy
{
    // For BackGround Image
    public Integer[] mThumbIds = {R.drawable.black, R.drawable.backa, R.drawable.backb, R.drawable.backc, R.drawable.backd,
            R.drawable.backe , R.drawable.backf, R.drawable.backg, R.drawable.backh, R.drawable.backi, R.drawable.backj,
            R.drawable.backk, R.drawable.backl, R.drawable.backm};

    private Bitmap bitmapDum=null;
    float x = 0.0f;
    float y = 0.0f;
    private FloatBuffer vertexBufferDum;
    private FloatBuffer texelBufferDum;
    private int[] scnTexturesDum = new int[1];
    float   vertex[] = new float[] {
            -x, y, 0,
            x, y, 0,
            -x,-y, 0,
            x,-y, 0
    };

    float   texel[] = new float[] {
            0, 0,
            1, 0,
            0, 1,
            1, 1
    };

    /**
     * The Cube constructor.
     *
     * Initiate the buffers.
     */
    public Dummy() {
        // For Background Image FullScreen
        ByteBuffer vb = ByteBuffer.allocateDirect(vertex.length * 4);
        vb.order(ByteOrder.nativeOrder());
        vertexBufferDum = vb.asFloatBuffer();
        vertexBufferDum.put(vertex);
        vertexBufferDum.position(0);

        ByteBuffer tb = ByteBuffer.allocateDirect(texel.length * 4);
        tb.order(ByteOrder.nativeOrder());
        texelBufferDum = tb.asFloatBuffer();
        texelBufferDum.put(texel);
        texelBufferDum.position(0);



    }

    /**
     * The object own drawing function.
     * Called from the renderer to redraw this instance
     * with possible changes in values.
     *
     * @param gl - The GL Context
     * @param filter - Which texture filter to be used
     */
    public void draw(GL10 gl, int filter) {
        // For Background image Full screen


        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(10.0f, 10.0f, -18.0f);
        gl.glDisable(GL10.GL_BLEND);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, scnTexturesDum[0]);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBufferDum);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texelBufferDum);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertex.length / 3);
        //gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        // gl.glDisable(GL10.GL_TEXTURE_2D);


    }

    /**
     * Load the textures
     *
     * @param gl - The GL Context
     * @param context - The Activity context
     */
    public void loadGLTexture(GL10 gl, Context context) {

        //For Background Image Full screen

        //Get the texture from the Android resource directory
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int userID= preferences.getInt("GImageBackground", mThumbIds[0]);
        String galPath=preferences.getString("GbackgroundPath", "");
        if(galPath.length()==0){
            bitmapDum = BitmapFactory.decodeStream(context.getResources().openRawResource(userID));
        }
        else{
            bitmapDum = BitmapFactory.decodeFile(galPath);
        }

        gl.glGenTextures(1, scnTexturesDum, 0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, scnTexturesDum[0]);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        //gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);
        //gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_CLAMP_TO_EDGE);

        //gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE,GL10.GL_REPLACE);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapDum, 0);



        //Clean up
        bitmapDum.recycle();
    }



    /**
     * Our own MipMap generation implementation.
     * Scale the original bitmap down, always by factor two,
     * and set it as new mipmap level.
     *
     * Thanks to Mike Miller (with minor changes)!
     *
     * @param gl - The GL Context
     * @param bitmap - The bitmap to mipmap
     */
	/*private void buildMipmap(GL10 gl, Bitmap bitmap) {
		//
		int level = 0;
		//
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();

		//
		while(height >= 1 || width >= 1) {
			//First of all, generate the texture from our bitmap and set it to the according level
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, level, bitmap, 0);

			//
			if(height == 1 || width == 1) {
				break;
			}

			//Increase the mipmap level
			level++;

			//
			height /= 2;
			width /= 2;
			Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, width, height, true);

			//Clean up
			bitmap.recycle();
			bitmap = bitmap2;
		}
	}*/
}
