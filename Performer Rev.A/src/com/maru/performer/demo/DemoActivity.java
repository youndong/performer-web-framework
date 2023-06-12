package com.maru.performer.demo;

import com.maru.performer.demo.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;

public class DemoActivity extends Activity {

	WebView mWebView = null;
	String mStringHead = null;
	com.maru.performer.framework.base.Settings mSettings = null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.default_layout);
		mWebView = (WebView) findViewById(R.id.webview);
		mSettings = new com.maru.performer.framework.base.Settings(this, mWebView, null);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
