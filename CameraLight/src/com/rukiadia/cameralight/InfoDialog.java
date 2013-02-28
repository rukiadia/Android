/* 説明ダイアログのカスタムクラス */
package com.rukiadia.cameralight;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoDialog extends Dialog {
	
	//ダイアログタイトル
	private TextView dialog_title;
	//本文
	private TextView dialog_text;
	//ボタン
	private Button dialog_button;

	public InfoDialog(Context context, String title, String text){
		super(context, R.style.Theme_CustomDialog);
		setContentView(R.layout.custom_dialog);
		
		//ダイアログのタイトル、本文、ボタン
		dialog_title = (TextView)findViewById(R.id.dialog_title);
		dialog_text = (TextView)findViewById(R.id.dialog_text);
		dialog_button = (Button)findViewById(R.id.dialog_button);
		
		//表示文設定
		dialog_title.setText(R.string.dialog_title);
		dialog_text.setText(R.string.dialog_text);
		
		//イベントリスナー
		dialog_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
	}
}
