package com.libertad.demo.common.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.libertad.demo.common.redis.RedisUtils;
import com.libertad.demo.common.result.JsonResult;
import com.libertad.demo.common.result.R;
import com.libertad.demo.common.startuprunner.StartupRunner;
import com.libertad.demo.common.token.BaseContext;
import com.libertad.demo.common.token.IgnoreUserToken;
import com.libertad.demo.common.token.UserTokenUtils;
import com.libertad.demo.common.utils.HttpContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


public class UserInterceptor implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(UserInterceptor.class);

	@Resource
	private RedisUtils redisUtils;

	@Value("config.privateKey")
	public static String priKey;
	/**
	 * 头
	 */
	private static final String AUTHORIZATION = "Authorization";

	/**
	 * 在请求处理之前进行调用（Controller方法调用之前）
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 配置该注解，说明不进行用户拦截
		if (!(handler instanceof HandlerMethod))
			return true;
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		IgnoreUserToken annotation = handlerMethod.getBeanType().getAnnotation(IgnoreUserToken.class);
		if (annotation == null) {
			annotation = handlerMethod.getMethodAnnotation(IgnoreUserToken.class);
		}
		if (annotation != null) {
			return true;
		}
		//验证token
		String token = request.getHeader(AUTHORIZATION);
		if (!StringUtils.isBlank(token)) {
			if(!token.substring(token.length()-1).equals("=") || token.length() < 128){
				HttpContextUtils.responseResult(response, JsonResult.error(R.LOGIN));
				return false;
			}
		}
		
		logger.info("请求头信息：" + token);
		try{
			//验证token是否在服务器登录
			String tokenDecrypt = UserTokenUtils.tokenDecrypt(token, StartupRunner.priKey);
			JSONObject object = JSONObject.parseObject(tokenDecrypt);
			String strId = redisUtils.get(object.getString("id"));
			if(StringUtils.isBlank(strId)){
				HttpContextUtils.responseResult(response, JsonResult.error(R.LOGIN));
				return false;
			}
			if(!strId.equals(token)){
				HttpContextUtils.responseResult(response, JsonResult.error(R.IS_LOGIN));
				return false;
			}
			// 判断时间是否在登录两小时内
			if (!UserTokenUtils.tokenDate(object.getDate("date"))) {
				HttpContextUtils.responseResult(response, JsonResult.error(R.LOGIN_DATE));
				return false;
			}
			// 解密后的数据存储在当次请求空间内,可以随时使用
			Map<String, Object> map = new HashMap<>();
			map.put("userId", object.get("id"));
			map.put("userName", object.get("name"));
			BaseContext.setAttributes(map);
			// 在日志中区分请求人
			MDC.put("userId", object.getString("id"));
		}catch (Exception e) {
			HttpContextUtils.responseResult(response, JsonResult.error(R.UNAUTHORIZED));
			return false;
		}
		
		return true;
	}

	/**
	 * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @throws Exception
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		MDC.clear();
		BaseContext.remove();// 防止内存溢出
	}


}
