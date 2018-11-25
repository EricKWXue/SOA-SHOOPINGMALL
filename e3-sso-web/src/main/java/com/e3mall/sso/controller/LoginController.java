package com.e3mall.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.sso.service.LoginService;
import com.e3mall.utils.CookieUtils;
import com.e3mall.utils.E3Result;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value="/page/login")
	public String showLogin(String redirect, Model model) {
		model.addAttribute("redirect", redirect);
		return "login";
	}
	
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	@ResponseBody
	public E3Result Login(String username, String password, HttpServletRequest request, HttpServletResponse response){
		E3Result result = loginService.login(username, password);
		if(result.getStatus() == 200){
			String token = result.getData().toString();
			CookieUtils.setCookie(request, response, "token", token);
		}
		return result;
	}
}
