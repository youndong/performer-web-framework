package com.maru.performer.framework.base;

import java.util.ArrayList;
import java.util.List;

import com.maru.performer.framework.Framework;
import com.maru.performer.framework.contact.ContactManager;
import com.maru.performer.framework.feature.FeatureMain;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class Settings {

	/**
	 * @author youndong.park
	 * 
	 */
	private class ChromeClient extends WebChromeClient {

		@Override
		public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
			//Log.d(Framework.CLASS_NAME, "[JsAlert] " + message);

			Toast.makeText(mContext, message, 2000).show();

			result.confirm();
			return true;
		}
	}

	public static final String	CLASS_NAME			= "Settings";
	private static List<Object>	mJSInterfaceObject	= new ArrayList<Object>();
	
	private static final String OPTION_FLASH = "flash";

	private static WebView		mWebView			= null;

	/**
	 * @param obj
	 * @param interfaceName
	 */
	public static void addJavascriptInterface(Object obj, String interfaceName) {
		mWebView.addJavascriptInterface(obj, interfaceName);
		mJSInterfaceObject.add(obj);
	}

	/**
	 * @param url
	 */
	public static void loadUrl(String url) {
		mWebView.loadUrl(url);
	}

	//private final Handler	handler			= new Handler();
	private FeatureMain		mBondi			= null;
	//private ContactManager	mContactManager	= null;
	private Context			mContext		= null;

	/**
	 * @param context
	 * @param webView
	 */
	public Settings(Context context, WebView webView, String options) {
		mContext = context;
		mWebView = webView;

		init();
		if (OPTION_FLASH.equals(options)) {
			Settings.loadUrl("file:///android_asset/default.swf");
			//Log.d(Framework.CLASS_NAME, OPTION_FLASH);
		}
		else
			Settings.loadUrl("file:///android_asset/env_setting.html");

		// Test test = new Test(mContext, mBondi);

		// test.run();
	}

	/**
	 * 
	 */
	public void init() {
		// Enable JavaScript
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setPluginsEnabled(true);
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.LOW);
		mWebView.getSettings().setDefaultZoom(ZoomDensity.FAR);

		ChromeClient cc = new ChromeClient();

		mWebView.setWebChromeClient(cc);
		mWebView.clearHistory();
		// Bridge 인스턴스 등록

		mBondi = new FeatureMain(mContext, mWebView);
		addJavascriptInterface(mBondi, "performer_bondi");

		addJavascriptInterface(new Framework(), "framework");
	}
}
