package uk.co.eelpieconsulting.landregistry.controllers;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component("corsHandler")
public class CORSHandler extends HandlerInterceptorAdapter {

	private static Logger log = Logger.getLogger(CORSHandler.class);

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		final String origin = request.getHeader("Origin");
		log.debug("Replying with Access-Control-Allow-Origin header: " + origin);
		response.addHeader("Access-Control-Allow-Origin", origin);
	}

}
