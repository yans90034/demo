package com.demo.form;

import java.util.HashMap;
import java.util.Map;

public class ResponseForm {

	private Boolean is_success;
	private Map<String, Object>content;
	
	public Boolean getIs_success() {
		return is_success;
	}
	public void setIs_success(Boolean is_success) {
		this.is_success = is_success;
	}
	public Map<String, Object> getContent() {
		return content;
	}
	public void setContent(Map<String, Object> content) {
		this.content = content;
	}

	public static ResponseForm getSuccessed() {
		return getForm(Boolean.TRUE, null);
	}
	
	public static ResponseForm getSuccessed(Map<String, Object> content) {
		return getForm(Boolean.TRUE, content);
	}
	
	public static ResponseForm getFailed(String msg) {
		Map<String, Object> map = new HashMap<>();
		map.put("msg", msg);
		return getForm(Boolean.FALSE, map);
	}
	
	private static ResponseForm getForm(Boolean result, Map<String, Object> content) {
		ResponseForm res = new ResponseForm();
		res.setIs_success(result);
		res.setContent(content);
		return res;
	}
	
}
