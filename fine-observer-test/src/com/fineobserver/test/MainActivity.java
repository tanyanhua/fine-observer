package com.fineobserver.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.fineobserver.FineObserver;
import com.fineobserver.FineObserverManager;
import com.fineobserver.test.bean.MessageOne;
import com.fineobserver.test.bean.MessageTwo;

public class MainActivity extends Activity {

	private TextView mTextView;

	private FineObserver<MessageOne> mMessageOneObserver = new FineObserver<MessageOne>() {

		@Override
		public void notify(MessageOne t) {
			if (t != null && t.mData != null) {
				Toast.makeText(getApplicationContext(), "MessageOne:"+t.mData, Toast.LENGTH_SHORT).show();
				mTextView.setText(t.mData);
			}
		}

		@Override
		public Class<MessageOne> getType() {
			return MessageOne.class;
		}
	};
	
	private FineObserver<MessageTwo> mMessageTwoObserver = new FineObserver<MessageTwo>() {

		@Override
		public void notify(MessageTwo t) {
			if (t != null && t.mData != null) {
				Toast.makeText(getApplicationContext(), "MessageOne:"+t.mData, Toast.LENGTH_SHORT).show();
				mTextView.setText(t.mData);
			}
		}

		@Override
		public Class<MessageTwo> getType() {
			return MessageTwo.class;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FineObserverManager.register(mMessageOneObserver);
		FineObserverManager.register(mMessageTwoObserver);
		
		setContentView(R.layout.activity_main);
		mTextView = (TextView) findViewById(R.id.textview);
		findViewById(R.id.button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getApplicationContext(), ActivityTwo.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		FineObserverManager.unregister(mMessageOneObserver);
		FineObserverManager.unregister(mMessageTwoObserver);
	}

}
