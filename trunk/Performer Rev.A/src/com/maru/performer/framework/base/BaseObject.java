package com.maru.performer.framework.base;

import org.json.JSONObject;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public abstract class BaseObject {
	/**
	 * 클래스 객체의 내부 데이터를 JSON 포맷으로 공유하고자 할 경우 사용된다.
	 * 
	 * @return JSONObject
	 */
	public abstract JSONObject getJSON();

	/**
	 * JSON 포맷의 데이터를 읽어 클래스 객체의 내부 데이터에 적용한다.
	 * 
	 * @param obj
	 */
	public abstract void setJSON(JSONObject obj);
}
