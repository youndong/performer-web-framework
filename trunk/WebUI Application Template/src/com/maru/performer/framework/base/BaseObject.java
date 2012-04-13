package com.maru.performer.framework.base;

import org.json.JSONObject;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public abstract class BaseObject {
	/**
	 * Ŭ���� ��ü�� ���� �����͸� JSON �������� �����ϰ��� �� ��� ���ȴ�.
	 * 
	 * @return JSONObject
	 */
	public abstract JSONObject getJSON();

	/**
	 * JSON ������ �����͸� �о� Ŭ���� ��ü�� ���� �����Ϳ� �����Ѵ�.
	 * 
	 * @param obj
	 */
	public abstract void setJSON(JSONObject obj);
}
