package com.js.reactive.app.jetty;

public class FilterItem {
	private final Class filterClass;
	private final String path;
	private final JettyApp.Dispatch dispatch;

	public FilterItem(Class filterClass, String path, JettyApp.Dispatch dispatch) {
		this.filterClass = filterClass;
		this.path = path;
		this.dispatch = dispatch;
	}

	public Class getFilterClass() {
		return filterClass;
	}

	public String getPath() {
		return path;
	}

	public JettyApp.Dispatch getDispatch() {
		return dispatch;
	}
}
