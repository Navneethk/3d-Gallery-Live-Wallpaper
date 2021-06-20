package com.drunk3nm.gallery;

import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MichaelWallpaperService extends GLWallpaperService {

    public static final String PREFERENCES = "Gallery.livewallpaper";

    private SensorManager sm;

    public MichaelWallpaperService() {
        super();
    }

    public Engine onCreateEngine() {
        MyEngine engine = new MyEngine();
        return engine;
    }

    class MyEngine extends GLEngine implements
            SharedPreferences.OnSharedPreferenceChangeListener,
            SensorEventListener {
        MyGLRenderer renderer;

        public MyEngine() {
            super();
            // handle prefs, other initialization
            renderer = new MyGLRenderer();
            renderer.setContext(getBaseContext());
            setRenderer(renderer);
            setRenderMode(RENDERMODE_CONTINUOUSLY);
        }

        public void onDestroy() {
            // Unregister this as listener
            sm.unregisterListener(this);

            // Kill renderer
            if (renderer != null) {
                renderer.release(); // assuming yours has this method - it should!
            }
            renderer = null;

            setTouchEventsEnabled(false);

            super.onDestroy();
        }


        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            if (renderer != null) {
                renderer.release(); // assuming yours has this method - it should!
            }
            renderer = null;

            setTouchEventsEnabled(false);
            super.onSurfaceDestroyed(holder);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            renderer.onTouchEvent(event);
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            // Add touch events
            setTouchEventsEnabled(true);

            // Get sensormanager and register as listener.
            sm = (SensorManager) getSystemService(SENSOR_SERVICE);
            @SuppressWarnings("deprecation")
            Sensor orientationSensor = sm.getDefaultSensor(SensorManager.SENSOR_ORIENTATION);
            sm.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_GAME);
        }

        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
        }
    }
}
