/*
 * 端末にインストールされている全アプリ情報を取得し、テキストファイルに保存するアプリ
 */

package com.rukiadia.applicationmanager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TopActivity extends Activity implements OnClickListener {
	
	final String ApplicationName = "ApplicationName";
	
	//アプリ名、パッケージ名、アプリバージョン、バージョンコード
	CharSequence appName;
	String packageName;
	String versionName;
	int versionCode;
	
	//保存先ディレクトリ情報
	String uri;
	//保存ファイル名
	File fileName;
	//端末モデル名
	String model = Build.MODEL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button output_btn = (Button)findViewById(R.id.output_button);
		output_btn.setOnClickListener(this);
	}
	
	/*
	 * ディレクトリ指定には、OIファイルマネージャを用いる
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.output_button:
			Intent intent = new Intent("org.openintents.action.PICK_DIRECTORY");
			intent.setData(Uri.parse("file://sdcard"));
			intent.putExtra("org.openintents.extra.TITLE", "Please select a folder");
			intent.putExtra("org.openintents.extra.BUTTON_TEXT", "Use this folder");
			//ディレクトリ情報取得
			startActivityForResult(intent, 1);
			break;
		}
	}
	
	//OIFilemanagerから結果を受け取る
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && requestCode == 1){
	        Log.d(ApplicationName, "onActivityResult:" + resultCode);
	        String fileURL = data.getData().toString();
	        fileURL = fileURL.substring(7);
	        //取得したディレクトリ情報を用いて、ファイル保存を行う
	        saveInfoDatas(fileURL);
		}
    }
	
	//アプリ情報保存メソッド
	public void saveInfoDatas(String fileURL){
		//テキストファイルのパス指定(SDカード内)
		String filePath = fileURL + "/" + model + "_appinfo.csv";
		Log.d(ApplicationName, filePath);
		fileName = new File(filePath);
		//過去に出力したファイルが有る場合は消去し、再構成
		Log.v(ApplicationName, "delete an old CSVfile");
		fileName.delete();
		fileName.getParentFile().mkdir();
		
		//アプリ情報のヘッダを記載
		try{
			FileOutputStream fos = new FileOutputStream(fileName, true);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "Shift_JIS");
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write("AppName," + "PackageName," + "VersionName," + "VersionCode");
			bw.newLine();
			bw.close();
		} catch(IOException e) {
			Log.e(ApplicationName, "failed to save headerdatas.");
			e.printStackTrace();
		}
		
		PackageManager manager = getPackageManager();
		List<ApplicationInfo> infos = manager.getInstalledApplications(PackageManager.GET_META_DATA);
		
		for(ApplicationInfo info : infos){
			
			//アプリ名取得
			appName = info.loadLabel(manager);
		
			//パッケージ名、VersionCode、VersionNameを取得
			try{
				PackageInfo packageInfo = manager.getPackageInfo(info.packageName, PackageManager.GET_META_DATA);
				packageName = info.packageName;
				versionName = packageInfo.versionName;
				versionCode = packageInfo.versionCode;
				Log.i(ApplicationName, "AppName: " + appName +", packageName: " + info.packageName + ", versionName: " + packageInfo.versionName + ", versionCode: " + packageInfo.versionCode);
			} catch(NameNotFoundException e) {
				Log.e(ApplicationName, "failed to get applicationdatas.");
				e.printStackTrace();
			}
			
			//アプリ情報をテキストファイルへ出力し、保存
			try{
				FileOutputStream fos2 = new FileOutputStream(fileName, true);
				OutputStreamWriter osw2 = new OutputStreamWriter(fos2, "Shift_JIS");
				BufferedWriter bw2 = new BufferedWriter(osw2);
				//ダブルクォーテーションで囲む
				bw2.write("\"" + appName + "\"," +
				"\"" + packageName + "\"," +
				"\"" + versionName + "\"," +
				"\"" + versionCode + "\"");
				bw2.newLine();
				bw2.flush();
				bw2.close();
			} catch(IOException e) {
				Log.e(ApplicationName, "failed to write dataset for text file.");
				e.printStackTrace();
			}
		}
		Toast.makeText(this, "complete to output", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onStart(){
		super.onStart();
		Log.v(ApplicationName, "onStart");
	}
	
	@Override
	protected void onResume(){
		super.onStart();
		Log.v(ApplicationName, "onResume");
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		Log.v(ApplicationName, "onPause");
	}
	
	@Override
	protected void onRestart(){
		super.onRestart();
		Log.v(ApplicationName, "onRestart");
	}
	
	@Override
	protected void onStop(){
		super.onStop();
		Log.v(ApplicationName, "onStop");
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.v(ApplicationName, "onDestroy");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
