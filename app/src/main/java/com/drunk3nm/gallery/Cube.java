package com.drunk3nm.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cube
{
    private FloatBuffer vertexBuffer; // Buffer for vertex-array
    private FloatBuffer texBuffer;    // Buffer for texture-coords-array (NEW)

    public Integer[] mThumbIds = {R.drawable.blacksquare, R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four,
            R.drawable.five, R.drawable.six, R.drawable.seven, R.drawable.eight,
            R.drawable.nine, R.drawable.ten, R.drawable.eleven, R.drawable.twelve, R.drawable.thirteen};

    private float[] vertices = { // Vertices for a face
            -0.5f, -0.5f, -0.5f,  // 0. left-bottom-front
            0.5f, -0.5f, -0.5f,  // 1. right-bottom-front
            -0.5f,  0.5f, -0.5f,  // 2. left-top-front
            0.5f,  0.5f, -0.5f   // 3. right-top-front
    };

    float[] texCoords = { // Texture coords for the above face (NEW)
            0.0f, 1.0f,  // A. left-bottom (NEW)
            1.0f, 1.0f,  // B. right-bottom (NEW)
            0.0f, 0.0f,  // C. left-top (NEW)
            1.0f, 0.0f   // D. right-top (NEW)
    };
    int[] textureIDs = new int[6];   // Array for 1 texture-ID (NEW)

    private int numFaces=6;
    /**
     * The Cube constructor.
     *
     * Initiate the buffers.
     */
    public Cube() {

        // Setup vertex-array buffer. Vertices in float. An float has 4 bytes
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder()); // Use native byte order
        vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
        vertexBuffer.put(vertices);         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind

        // Setup texture-coords-array buffer, in float. An float has 4 bytes (NEW)
        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        texBuffer = tbb.asFloatBuffer();
        texBuffer.put(texCoords);
        texBuffer.position(0);

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

        gl.glFrontFace(GL10.GL_CCW);    // Front face in counter-clockwise orientation
        // comment gl_cull_face to make it transparent==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        gl.glEnable(GL10.GL_CULL_FACE); // Enable cull face
        gl.glCullFace(GL10.GL_BACK);    // Cull the back face (don't display)

        // For transparent checking =-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
       // gl.glEnable(GL10.GL_BLEND);
       // gl.glBlendFunc(GL10.GL_SRC_ALPHA,GL10.GL_ONE_MINUS_SRC_ALPHA);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);  // Enable texture-coords-array (NEW)
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer); // Define texture-coords buffer (NEW)

        int[] maxSize = new int[1];
        gl.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxSize, 0);

        // front
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, 1.0f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        gl.glPopMatrix();

        // left
        gl.glPushMatrix();
        gl.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);

        gl.glTranslatef(0.0f, 0.0f, 1.0f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[1]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        gl.glPopMatrix();

        // back
        gl.glPushMatrix();
        gl.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        gl.glTranslatef(0.0f, 0.0f, 1.0f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[2]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        gl.glPopMatrix();

        // right
        gl.glPushMatrix();
        gl.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
        gl.glTranslatef(0.0f, 0.0f, 1.0f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[3]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        gl.glPopMatrix();

        // top
        gl.glPushMatrix();
        gl.glRotatef(270.0f, 1.0f, 0.0f, 0.0f);
        gl.glTranslatef(0.0f, 0.0f, 1.0f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[4]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        gl.glPopMatrix();

        // bottom
        gl.glPushMatrix();
        gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        gl.glTranslatef(0.0f, 0.0f, 1.0f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[5]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        gl.glPopMatrix();

        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);  // Disable texture-coords-array (NEW)
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
    }

    /**
     * Load the textures
     *
     * @param gl - The GL Context
     * @param context - The Activity context
     */
    public void loadGLTexture(GL10 gl, Context context) {
        Bitmap bitmap = null;
        InputStream istream;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        int imagefront = 0;
        int imageleft = 0;
        int imageback = 0;
        int imagetop = 0;
        int imagebottom = 0;
        int imageright = 0;
        imagefront= preferences.getInt("GImageFront", mThumbIds[13]);
        imageleft=preferences.getInt("GImageLeft", mThumbIds[13]);
        imageback=preferences.getInt("GImageBack",mThumbIds[13]);
        imageright=preferences.getInt("GImageRight",mThumbIds[13]);
        imagetop=preferences.getInt("GImageTop",mThumbIds[13]);
        imagebottom=preferences.getInt("GImageBottom",mThumbIds[13]);
        int front=imagefront;
        int left=imageleft;
        int back=imageback;
        int right=imageright;
        int top=imagetop;
        int bottom=imagebottom;
        int[] imageFileIDs = {  // Image file IDs
                imagefront,
                imageleft,
                imageback,
                imageright,
                imagetop,
                imagebottom
        };

        gl.glGenTextures(6, textureIDs, 0); // Generate texture-ID array
        for (int face = 0; face < numFaces; face++) {


            gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[face]);   // Bind to texture ID
            // Set up texture filters
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

            if(face==0)
            {
                String galPath=preferences.getString("GfrontPath", "");
                if(galPath.length()==0)
                {
                    // Construct an input stream to texture image "res\drawable\nehe.png"
                    istream = context.getResources().openRawResource(imageFileIDs[face]);

                    try
                    {
                        bitmap = BitmapFactory.decodeStream(istream);
                    }finally
                    {
                        try
                        {
                            istream.close();
                        }catch(IOException e) { }
                    }
                }
                else
                {
                    try
                    {
                        bitmap = BitmapFactory.decodeFile(galPath);
                    }catch(Exception e){}

                }
                // Build Texture from loaded bitmap for the currently-bind texture ID
                GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
                // bitmap.recycle();
            }
            if(face==1)
            {
                String galPath=preferences.getString("GleftPath", "");
                if(galPath.length()==0)
                {
                    // Construct an input stream to texture image "res\drawable\nehe.png"
                    istream = context.getResources().openRawResource(imageFileIDs[face]);

                    try
                    {
                        bitmap = BitmapFactory.decodeStream(istream);
                    }finally
                    {
                        try
                        {
                            istream.close();
                        }catch(IOException e) { }
                    }
                }
                else
                {
                    // Construct an input stream to texture image "res\drawable\nehe.png"

                    try
                    {
                        bitmap = BitmapFactory.decodeFile(galPath);
                    }catch(Exception e){}
                }
                // Build Texture from loaded bitmap for the currently-bind texture ID
                GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
                // bitmap.recycle();
            }
            if(face==2)
            {
                String galPath=preferences.getString("GbackPath", "");
                if(galPath.length()==0)
                {
                    // Construct an input stream to texture image "res\drawable\nehe.png"
                    istream = context.getResources().openRawResource(imageFileIDs[face]);

                    try
                    {
                        bitmap = BitmapFactory.decodeStream(istream);
                    }finally
                    {
                        try
                        {
                            istream.close();
                        }catch(IOException e) { }
                    }
                }
                else
                {
                    try
                    {
                        bitmap = BitmapFactory.decodeFile(galPath);
                    }catch(Exception e){}
                }
                // Build Texture from loaded bitmap for the currently-bind texture ID
                GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
                // bitmap.recycle();
            }
            if(face==3)
            {
                String galPath=preferences.getString("GrightPath", "");
                if(galPath.length()==0)
                {
                    // Construct an input stream to texture image "res\drawable\nehe.png"
                    istream = context.getResources().openRawResource(imageFileIDs[face]);

                    try
                    {
                        bitmap = BitmapFactory.decodeStream(istream);
                    }finally
                    {
                        try
                        {
                            istream.close();
                        }catch(IOException e) { }
                    }
                }
                else
                {
                    try
                    {
                        bitmap = BitmapFactory.decodeFile(galPath);
                    }catch(Exception e){}
                }
                // Build Texture from loaded bitmap for the currently-bind texture ID
                GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
                // bitmap.recycle();
            }
            if(face==4)
            {
                String galPath=preferences.getString("GtopPath", "");
                if(galPath.length()==0)
                {
                    // Construct an input stream to texture image "res\drawable\nehe.png"
                    istream = context.getResources().openRawResource(imageFileIDs[face]);

                    try
                    {
                        bitmap = BitmapFactory.decodeStream(istream);
                    }finally
                    {
                        try
                        {
                            istream.close();
                        }catch(IOException e) { }
                    }
                }
                else
                {
                    try
                    {
                        bitmap = BitmapFactory.decodeFile(galPath);
                    }catch(Exception e){}
                }
                // Build Texture from loaded bitmap for the currently-bind texture ID
                GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
                // bitmap.recycle();
            }
            if(face==5)
            {
                String galPath=preferences.getString("GbottomPath", "");
                if(galPath.length()==0)
                {
                    // Construct an input stream to texture image "res\drawable\nehe.png"
                    istream = context.getResources().openRawResource(imageFileIDs[face]);

                    try
                    {
                        bitmap = BitmapFactory.decodeStream(istream);
                    }finally
                    {
                        try
                        {
                            istream.close();
                        }catch(IOException e) { }
                    }
                }
                else
                {
                    try
                    {
                        bitmap = BitmapFactory.decodeFile(galPath);
                    }catch(Exception e){}
                }
                // Build Texture from loaded bitmap for the currently-bind texture ID
                GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
                // bitmap.recycle();
            }


        }
    }


    /**
     * Our own MipMap generation implementation.
     * Scale the original bitmap down, always by factor two,
     * and set it as new mipmap level.
     *
     * with minor changes
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
