package com.example.sip_camera_1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.net.rtp.RtpStream;
import android.util.Log;

public class RtpService extends Service {

	public RtpStream streamRTP;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
