package com.e3mall.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.e3mall.pojo.TbUser;
import com.e3mall.sso.service.TokenService;
import com.e3mall.utils.CookieUtils;
import com.e3mall.utils.E3Result;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	private TokenService tokenService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//执行handler之前
		String token = CookieUtils.getCookieValue(request, "token");
		if(StringUtils.isBlank(token)){
			return true;
		}
		E3Result userInfo = tokenService.getUserByToken(token);
		if(userInfo.getStatus() != 200){
			return true;
		}
		TbUser user = (TbUser)userInfo.getData();
		request.setAttribute("user", user);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model)
			throws Exception {
		// /执行handler之后，返回模型视图之前
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 返回模型视图之后，处理异常

	}



}
