package com.example.sip_camera_1;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import com.example.sip_camera_1.R;
import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
//import android.view.ViewGroup.LayoutParams;


public class CameraActivity extends Activity {
  private static final String TAG = "CameraDemo";
  Preview preview; // <1>
  Button buttonClick; // <2>
  //Camera camera;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_camera);

    preview = new Preview(this); // <3>
    //preview.camera.setDisplayOrientation(90);
    //preview.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
    ((FrameLayout) findViewById(R.id.preview)).addView(preview); // <4>

    buttonClick = (Button) findViewById(R.id.buttonClick);
    buttonClick.setOnClickListener(new OnClickListener() {
      public void onClick(View v) { // <5>
        preview.camera.takePicture(shutterCallback, rawCallback, jpegCallback);
        //((FrameLayout) findViewById(R.id.preview)).removeView(preview); 
        //((FrameLayout) findViewById(R.id.preview)).addView(preview); 
      }
    });

    Log.d(TAG, "onCreate'd");
    
  }
  
	@Override
	protected void onResume() {
		super.onResume();
	}
  
	@Override
	protected void onPause() {
		preview.camera.stopPreview();
		preview.camera.release();
		super.onPause();
	}
  
  // Called when shutter is opened
  ShutterCallback shutterCallback = new ShutterCallback() { // <6>
    public void onShutter() {
      Log.d(TAG, "onShutter'd"); 
    }
  };

  // Handles data for raw picture
  PictureCallback rawCallback = new PictureCallback() { // <7>
    public void onPictureTaken(byte[] data, Camera camera) {
      Log.d(TAG, "onPictureTaken - raw");
    }
  };

  // Handles data for jpeg picture
  PictureCallback jpegCallback = new PictureCallback() { // <8>
    public void onPictureTaken(byte[] data, Camera camera) {
      FileOutputStream outStream = null;
      try {
        // Write to SD Card
        outStream = new FileOutputStream(String.format("/sdcard/%d.jpg",
            System.currentTimeMillis())); // <9>
        outStream.write(data);
        outStream.close();
        Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
      } catch (FileNotFoundException e) { // <10>
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
      }
      Log.d(TAG, "onPictureTaken - jpeg");
      camera.startPreview();
    }
  };

}