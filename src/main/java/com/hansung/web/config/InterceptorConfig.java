/*
 * package com.hansung.web.config;
 * 
 * import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
 * import lombok.extern.slf4j.Slf4j;
 * 
 * @Slf4j public class InterceptorConfig extends HandlerInterceptorAdapter {
 * 
 * @Override public boolean preHandle(HttpServletRequest request,
 * HttpServletResponse response, Object handler) throws Exception {
 * log.info("Interceptor > preHandle"); return true; }
 * 
 * @Override public void postHandle(HttpServletRequest request,
 * HttpServletResponse response, Object handler, ModelAndView modelAndView)
 * 
 * throws Exception { log.info("Interceptor > postHandle"); }
 * 
 * @Override public void afterCompletion(HttpServletRequest request,
 * HttpServletResponse response, Object object, Exception arg3) throws Exception
 * { log.info("Interceptor > afterCompletion"); }
 * 
 * }
 */