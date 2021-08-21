package com.js.reactive.app.jetty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.js.reactive.app.servlet.GenericServlet;
import com.js.reactive.app.utils.EndPoints;

/**
 * Uses implementation of servlet 3.1. This is an implementation of non-blocking
 * servlet
 * 
 * @author demo
 *
 */
public class ReactiveJ {
	private final static Logger log = Logger.getLogger(ReactiveJ.class.getName());

	private int port;
	private List<EndPoints> endpointsList = new ArrayList<>();
	private Map<Class, String> servletsMap = new HashMap<>();
	private List<FilterItem> filterItemList = new ArrayList<>();

	public ReactiveJ port(int port) {
		this.port = port;
		return this;
	}

	public ReactiveJ endpoints(EndPoints endpoints) {
		endpointsList.add(endpoints);
		return this;
	}

	/* if you want to add some other custom Servlets */
	public ReactiveJ addServlet(Class servletClass, String path) {
		this.servletsMap.put(servletClass, path);
		return this;
	}

	/* if you want to add some other custom Filters */
	public ReactiveJ addFilter(Class filterClass, String path, JettyApp.Dispatch dispatch) {
		this.filterItemList.add(new FilterItem(filterClass, path, dispatch));
		return this;
	}

	public void start() throws Exception {
		JettyApp jetty = new JettyApp().port(port).servlet(GenericServlet.class, "/*");
		if (!servletsMap.isEmpty()) {
			for (Map.Entry<Class, String> entry : servletsMap.entrySet()) {
				jetty.servlet(entry.getKey(), entry.getValue());
				log.info(LocalDateTime.now() + ": " + this.getClass().getSimpleName() + " | " + "Added servlet "
						+ entry.getKey() + "at path " + entry.getValue());
			}
		}
		if (!filterItemList.isEmpty()) {
			for (FilterItem filter : filterItemList) {
				jetty.filter(filter.getFilterClass(), filter.getPath(), filter.getDispatch());
				log.info(LocalDateTime.now() + ": " + this.getClass().getSimpleName() + " | " + "Added filter "
						+ filter.getFilterClass() + "at path " + filter.getPath());
			}
		}
		jetty.start();
	}
}
