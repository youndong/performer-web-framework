package com.maru.performer.framework.feature;

import android.content.Context;
import android.webkit.WebView;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public abstract class FeatureBase {
	private static final String	CLASS_NAME			= "FeatureBase";
	private static final String	DEFAULT_DESCRIPTION	= "No descriptions";

	public static final int		STATUS_ENABLED		= 1;
	public static final int		STATUS_UNABLED		= 2;
	public static final int		STATUS_TERMINATED	= 3;

	private FeatureMain			mBondi				= null;
	protected Context			mContext			= null;
	public WebView				mWebView			= null;

	/**
	 * FeatureBase�� ������
	 * 
	 * @param context
	 *            feature�� ���� �Ǵ� ����, �����Ǿ�� �� context
	 * @param webView
	 *            feature�� ���� �Ǵ� ����, ��µǴ� view
	 */
	public FeatureBase(Context context, WebView webView) {
		this.mContext = context;
		this.mWebView = webView;
	}

	/**
	 * feature�� ���� ������ �߻��ϸ� �˷��ش�.
	 * 
	 * @param status
	 *            ���� ����
	 * @param desc
	 *            ���� ���¿� ���� ����
	 */
	public void changeStatus(int status, String desc) {
		if (mBondi != null) {
			if (desc != null)
				mBondi.notifyFeatureChanged(this, status, desc);
			else
				mBondi.notifyFeatureChanged(this, status, DEFAULT_DESCRIPTION);
		}
	}

	/**
	 * Create a class instance for JavaScript
	 * 
	 * @return
	 */
	public abstract String createJavascriptInstance();

	/**
	 * @param bondi
	 */
	public void setStatusChangeListener(FeatureMain bondi) {
		this.mBondi = bondi;
	}

}
