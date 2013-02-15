package com.rukiadia.guchitter;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TopActivity extends Activity implements OnClickListener {
	
	private Button savebtn;
	private Button everbtn;
	private TextView textCounter;
	private EditText editText1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top);
		
		savebtn = (Button)findViewById(R.id.save_button);
		everbtn = (Button)findViewById(R.id.ever_button);
		//デフォルトではbuttonが無効になっている
		savebtn.setEnabled(false);
		everbtn.setEnabled(false);
		
		textCounter = (TextView)findViewById(R.id.textCounter);
		textCounter.setText(R.string.alert_Message);
		editText1 = (EditText)findViewById(R.id.editText1);
		//EditTextの入力数に応じて、ボタンの有効無効を切り替える
		editText1.addTextChangedListener(new TextWatcher(){
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count){
				savebtn.setEnabled(false);
				everbtn.setEnabled(false);
			}
			@Override
			public void afterTextChanged(Editable s){
				if(editText1.length() != 0){
					savebtn.setEnabled(true);
					everbtn.setEnabled(true);
					textCounter.setText(R.string.help_message);
				} else {
					textCounter.setText(R.string.alert_Message);
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
		});
		savebtn.setOnClickListener(this);
		everbtn.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v){
		switch(v.getId()){
		case R.id.save_button:
			Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
			break;
		case R.id.ever_button:
			Toast.makeText(this, "link", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_top, menu);
		return true;
	}

}
