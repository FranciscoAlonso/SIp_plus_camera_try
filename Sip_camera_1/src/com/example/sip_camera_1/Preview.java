package com.example.sip_camera_1;


import java.io.IOException;

import com.example.sip_camera_1.Preview;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


public class Preview extends SurfaceView implements SurfaceHolder.Callback { // <1>
	  private static final String TAG = "Preview";

	  SurfaceHolder mHolder;  // <2>
	  public Camera camera; // <3>
	  Context contexto;

	  Preview(Context context) {
	    super(context);
	    contexto = context;

	    // Install a SurfaceHolder.Callback so we get notified when the
	    // underlying surface is created and destroyed.
	    mHolder = getHolder();  // <4>
	    mHolder.addCallback(this);  // <5>
	    mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); // <6>
	  }

	  // Called once the holder is ready
	  public void surfaceCreated(SurfaceHolder holder) {  // <7>
	    // The Surface has been created, acquire the camera and tell it where
	    // to draw.
	    camera = Camera.open(); // <8>
	    try {
	    	if((getRotation(contexto)==0)||(getRotation(contexto)==180)){
	    		camera.setDisplayOrientation(90);
	    	}
	      camera.setPreviewDisplay(holder);  // <9>

	      camera.setPreviewCallback(new PreviewCallback() { // <10>
	        // Called for each frame previewed
	        public void onPreviewFrame(byte[] data, Camera camera) {  // <11>
	          //Log.d(TAG, "onPreviewFrame called at: " + System.currentTimeMillis());
	          Preview.this.invalidate();  // <12>
	        }
	      });
	    } catch (IOException e) { // <13>
	      e.printStackTrace();
	    }
	  }

	  // Called when the holder is destroyed
	  public void surfaceDestroyed(SurfaceHolder holder) {  // <14>
	    camera.release();
	    camera = null;
	  }

	  // Called when holder has changed
	  public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) { // <15>
	    camera.startPreview();
	  }

	  public int getRotation(Context context){
		    final int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
		           switch (rotation) {
		            case Surface.ROTATION_0:
		                return 0;
		            case Surface.ROTATION_90:
		                return 90;
		            case Surface.ROTATION_180:
		                return 180;
		            default:
		                return 270;
		            }
		        }

}
