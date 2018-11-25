package com.e3mall.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.e3mall.cart.service.CartService;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbUser;
import com.e3mall.sso.service.TokenService;
import com.e3mall.utils.CookieUtils;
import com.e3mall.utils.E3Result;
import com.e3mall.utils.JsonUtils;

public class LoginInterceptor implements HandlerInterceptor {
	@Value("${SSO_URL}")
	private String SSO_URL;
	
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CartService cartService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = CookieUtils.getCookieValue(request, "token");
		if(StringUtils.isBlank(token)){
			response.sendRedirect(SSO_URL + "/page/login?redirect=" + request.getRequestURL());
			return false;
		}
		E3Result userInfo = tokenService.getUserByToken(token);
		if(userInfo.getStatus() != 200){
			response.sendRedirect(SSO_URL + "/page/login?redirect=" + request.getRequestURL());
			return false;
		}
		TbUser user = (TbUser)userInfo.getData();
		request.setAttribute("user", user);
		
		String cartJson = CookieUtils.getCookieValue(request, "cart", true);
		if(StringUtils.isNotBlank(cartJson)){
			cartService.mergeCart(user.getId(), JsonUtils.jsonToList(cartJson, TbItem.class));
			CookieUtils.setCookie(request, response, "cart", "");
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model)
			throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
