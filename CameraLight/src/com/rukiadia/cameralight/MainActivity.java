package com.rukiadia.cameralight;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;
import android.hardware.Camera;

public class MainActivity extends Activity implements OnCheckedChangeListener{
	
	ToggleButton tb1;
	Camera camera;
	Camera.Parameters cp;
	CompoundButton buttonView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tb1 = (ToggleButton)findViewById(R.id.toggleButton1);
		tb1.setOnCheckedChangeListener(this);
		
	}
	
	@Override
	public void onPause(){
		super.onPause();
		finish();
	}
	
	@Override
	public void onStop(){
		super.onStop();
		//制御しているカメラデバイスのインスタンス
		//アプリが終了した時も消灯
		camera.release();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		//ToggleButtonの処理
		if (buttonView.getId() == R.id.toggleButton1) {
			if(isChecked == true) {
				//ライトの点灯
				camera = Camera.open();
				cp = camera.getParameters();
				cp.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
				camera.setParameters(cp);
				camera.startPreview();
			} else if(isChecked == false) {
				//ライトの消灯
				camera.release();
			}
		}
	}

}
