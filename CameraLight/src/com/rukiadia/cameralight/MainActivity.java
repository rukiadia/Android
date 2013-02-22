/*
 * スイッチでON/OFFを切り替える(だけ)懐中電灯アプリケーション
 */
package com.rukiadia.cameralight;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;
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
		setContentView(R.layout.main);
		
		tb1 = (ToggleButton)findViewById(R.id.toggleButton1);
		tb1.setOnCheckedChangeListener(this);
		//バックライトの自動消灯をON
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
	
	@Override	
	public void onPause(){
		super.onPause();
		//スリープ時は消灯しない
		//camera.release();
	}
		
	@Override
	public void onDestroy(){
		super.onDestroy();
		//カメラオブジェクトを掴んでいない時は処理を行わない
		if (camera != null){
			camera.release();
		}
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
				//自動消灯をOFFに
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			} else if(isChecked == false) {
				//ライトの消灯
				camera.release();
				//自動消灯をONに
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		Toast.makeText(this, "Option", Toast.LENGTH_SHORT).show();
		return super.onOptionsItemSelected(item);
		
	}
}
