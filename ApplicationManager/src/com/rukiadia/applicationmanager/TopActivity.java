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

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class TopActivity extends Activity {
	
	//アプリ名、パッケージ名、アプリバージョン、バージョンコード
	CharSequence appName;
	String packageName;
	String versionName;
	int versionCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//アプリ起動と同時にテキストファイル出力
		//テキストファイルのパス指定(SDカード内)
		String filePath = Environment.getExternalStorageDirectory() + "/Download/appinfo.csv";
		File fileName = new File(filePath);
		//過去に出力したファイルが有る場合は消去
		Log.v("PackageManager", "delete an old CSVfile");
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
			Log.e("PackageManager", "failed to save headerdatas.");
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
				Log.i("PackageManager", "AppName: " + appName +", packageName: " + info.packageName + ", versionName: " + packageInfo.versionName + ", versionCode: " + packageInfo.versionCode);
			} catch(NameNotFoundException e) {
				Log.e("PackageManager", "failed to get applicationdatas.");
				e.printStackTrace();
			}
			
			//アプリ情報をテキストファイルへ書き込み、SDカードに保存
			try{
				FileOutputStream fos2 = new FileOutputStream(fileName, true);
				OutputStreamWriter osw2 = new OutputStreamWriter(fos2, "Shift_JIS");
				BufferedWriter bw2 = new BufferedWriter(osw2);
				/*
				bw.write("AppName: " + appName + ", packageName: " +
				packageName + ", versionName: " + versionName + ", versionCode: " + versionCode);
				*/
				//ダブルクォーテーションで囲み、CSVファイル形式で保存
				bw2.write("\"" + appName + "\"," +
				"\"" + packageName + "\"," +
				"\"" + versionName + "\"," +
				"\"" + versionCode + "\"");
				bw2.newLine();
				bw2.flush();
				bw2.close();
			} catch(IOException e) {
				Log.e("PackageManager", "failed to write dataset for text file.");
				e.printStackTrace();
			}
		}
		Toast.makeText(this, "出力しました", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
