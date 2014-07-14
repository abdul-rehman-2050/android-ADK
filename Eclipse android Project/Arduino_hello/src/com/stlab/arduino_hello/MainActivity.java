package com.stlab.arduino_hello;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity 
{

	private ArduinoAdk mArduinoAdk;
	private int Stato;
	
	
	public TextView tv_status;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Stato = 0;

		mArduinoAdk = new ArduinoAdk(this);		
		mArduinoAdk.connect();


		final Button button = (Button) findViewById(R.id.button1);
		tv_status = (TextView)findViewById(R.id.textView1);
		button.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v){

					if (Stato == 0) {
						mArduinoAdk.write(255);
						Stato = 1;
					} else {
						mArduinoAdk.write(0);
						Stato = 0;
					}

				}

		});


		arduinoRead.start();
		
		
	}

	//===========================================================
	
	Handler handler=new Handler();
	
	public class PostOnGUI implements Runnable{
		
		String msg = null;
		
		public PostOnGUI(String mg)
		{
			msg = mg;
		}

		@Override
		public void run() {
			
			tv_status.setText(msg);
			
		}
		
	}
	//=============================================================
	
	Thread arduinoRead = new Thread(new Runnable() {
		
		@Override
		public void run() {
			
		     while(true)
		     {
		    	 int val =  mArduinoAdk.read();
		    	 if(val>-1)
		    	 {
		    		 	int val2 = mArduinoAdk.read();
		    		 	int val3 = mArduinoAdk.read();
		    		 handler.post(new PostOnGUI("Arduino says:["+val+","+val2+","+val3+"]"));
		    		 
		    	 }    	 
		    	 
		    	 
		     }
			
			
		}
	});
	//==============================================================
	
}	
	
