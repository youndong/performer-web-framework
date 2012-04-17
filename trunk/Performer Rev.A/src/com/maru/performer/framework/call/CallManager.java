package com.maru.performer.framework.call;

import com.maru.performer.framework.feature.FeatureBase;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.webkit.WebView;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class CallManager extends FeatureBase {

	public static final String	CLASS_NAME	= "CallManager";

	private final Handler		handler		= new Handler();

	/**
	 * @param context
	 * @param webView
	 */
	public CallManager(Context context, WebView webView) {
		super(context, webView);
	}

	/**
	 * @param dialNo
	 */
	public void call(String dialNo) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + dialNo));
		mContext.startActivity(intent);
	}

	@Override
	public String createJavascriptInstance() {
		// TODO Auto-generated method stub
		return null;
	}
}
