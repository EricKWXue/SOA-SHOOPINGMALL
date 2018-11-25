package com.e3mall.sso.service;

import com.e3mall.utils.E3Result;

public interface TokenService {
	/**
	 * 根据token获取用户信息
	 * @param token
	 * @return
	 */
	E3Result getUserByToken(String token);
}
