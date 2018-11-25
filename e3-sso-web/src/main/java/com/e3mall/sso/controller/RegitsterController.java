package com.e3mall.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.pojo.TbUser;
import com.e3mall.sso.service.RegisterService;
import com.e3mall.utils.E3Result;

/**
 * 注册功能Controller
 */
@Controller
public class RegitsterController {

	@Autowired
	private RegisterService registerService;	
	
	@RequestMapping("/page/register")
	public String showRegister() {
		return "register";
	}
	
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public E3Result checkData(@PathVariable String param, @PathVariable Integer type){
		return registerService.checkData(param, type);
	}
	
	@RequestMapping(value="/user/register", method=RequestMethod.POST)
	@ResponseBody
	public E3Result register(TbUser user){
		return registerService.registUser(user);
	}
}
