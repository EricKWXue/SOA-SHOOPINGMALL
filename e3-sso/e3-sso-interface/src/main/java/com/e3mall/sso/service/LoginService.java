package com.e3mall.sso.service;

import com.e3mall.utils.E3Result;

public interface LoginService {
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	E3Result login(String username, String password);
}
