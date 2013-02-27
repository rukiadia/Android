/*
 * スイッチでON/OFFを切り替える(だけ)懐中電灯アプリケーション
 */
package com.rukiadia.cameralight;

import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;
import android.hardware.Camera;

public class MainActivity extends Activity implements OnCheckedChangeListener{
	
	private String ApplicationName = "CameraLight";
	
	//ノーティフィケーション情報
	private static final int NotificationID = 100;
	private NotificationManager manager;
	
	//トグルボタン、カメラオブジェクト
	private ToggleButton tb1;
	private Camera camera;
	private Camera.Parameters cp;

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
		Log.d(ApplicationName, "onResume");
	}
	
	@Override	
	public void onPause(){
		super.onPause();
		Log.d(ApplicationName, "onPause");
		//スリープ時もライトを消さない
		//camera.release();
	}
		
	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.d(ApplicationName, "onDestroy");
		//カメラオブジェクトを掴んでいない時は処理を行わない
		if (camera != null){
			camera.release();
		}
		//アプリ終了時にノーティフィケーションも消去
		if (manager != null){
			manager.cancel(NotificationID);
		}
	}

	/* トグルボタンの処理 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (buttonView.getId() == R.id.toggleButton1) {
			if(isChecked == true) {
				//ライトの点灯処理
				camera = Camera.open();
				cp = camera.getParameters();
				cp.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
				camera.setParameters(cp);
				camera.startPreview();
				//端末の画面が消灯しないようにする
				Log.d(ApplicationName, "ScreenOut ON");
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				//ノーティフィケーション表示
				showNotification();
			} else if(isChecked == false) {
				//ライトの消灯
				camera.release();
				//端末の画面が消灯するようにする
				Log.d(ApplicationName, "ScreenOut OFF");
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				
				//ライトを消したら、ノーティフィケーションも消去
				if (manager != null){
					manager.cancel(NotificationID);
				}
			}
		}
	}
	
	/* ノーティフィケーション処理 */
	public void showNotification(){
		
		Notification.Builder builder = new Notification.Builder(this);
		//アイコン
		builder.setSmallIcon(R.drawable.lightning);
		//ステータスバーに表示されるタイトル
		builder.setTicker("light up!");
		//ノーティフィケーションが発行されるタイミング
		builder.setWhen(System.currentTimeMillis());
		
		//ステータスバーを引き出した時に表示されるタイトル
		builder.setContentTitle(ApplicationName);
		//〜に表示される内容
		builder.setContentText("the camera light lit up");
		
		//ノーティフィケーションをタップした時の処理インテント
		Intent notificationIntent = new Intent(this, MainActivity.class);
		//上記のインテントをアクティビティとして起動するためのPendingIntent
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		//タップで起動するインテントをセット
		builder.setContentIntent(pIntent);
		
		Notification notification = builder.getNotification();
		
		//発行
		//NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(NotificationID, notification);
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
