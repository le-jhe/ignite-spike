package org.jhe.ignite.spike.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class IgniteSpikeInterceptor extends HandlerInterceptorAdapter{
	private static final Logger LOGGER = LoggerFactory.getLogger(IgniteSpikeInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest requestServlet, HttpServletResponse responseServlet, Object handler)
	{
		try {
			String referer = "";
			if (StringUtils.isNotBlank(requestServlet.getHeader("X-Forwarded-For"))) {
				referer = requestServlet.getHeader("X-Forwarded-For");
			}
			if (StringUtils.isBlank(referer)) {
				referer = requestServlet.getRemoteAddr();
			}
			MDC.put("Referer", referer);
			// no spring-security yet --V
			// MDC.put("User",
			// SecurityContextHolder.getContext().getAuthentication().getName());
		} catch(Exception e) {
			LOGGER.error("exception on logging interceptor",e);
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
	{
		try {
			LOGGER.debug("nothing to do yet");
		} catch(Exception e) {
			LOGGER.error("exception after completion", e);
		} finally {
			MDC.remove("Referer");
			MDC.remove("User");
		}
	}
}
