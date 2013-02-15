package com.rukiadia.cameralight;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.hardware.Camera;

public class MainActivity extends Activity implements OnClickListener{
	
	Camera c = Camera.open();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Camera.Parameters cp = c.getParameters();
		cp.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
		c.setParameters(cp);
		c.startPreview();
		
		Button on_button = (Button)findViewById(R.id.on_button);
		Button off_button = (Button)findViewById(R.id.off_button);
		on_button.setOnClickListener(this);
		off_button.setOnClickListener(this);
		
	}
	
	public void onClick(View v){
		switch(v.getId()){
			case R.id.on_button:
				Toast.makeText(this, "ON", Toast.LENGTH_SHORT).show();
				break;
			case R.id.off_button:
				Toast.makeText(this, "OFF", Toast.LENGTH_SHORT).show();
				break;
		}
	}
	
	@Override
	public void onStop(){
		super.onStop();
		//制御しているカメラデバイスのインスタンス
		c.release();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
