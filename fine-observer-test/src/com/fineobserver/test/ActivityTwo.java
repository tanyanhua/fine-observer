package com.fineobserver.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.fineobserver.FineObserverManager;
import com.fineobserver.test.bean.MessageOne;
import com.fineobserver.test.bean.MessageTwo;

public class ActivityTwo extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two);
		findViewById(R.id.button_one).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MessageOne messageOne = new MessageOne();
				messageOne.mData = "click button one";
				FineObserverManager.notify(messageOne);
			}
		});
		findViewById(R.id.button_two).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MessageTwo messageTwo = new MessageTwo();
				messageTwo.mData = "click button two";
				FineObserverManager.notify(messageTwo);
			}
		});
	}
}
