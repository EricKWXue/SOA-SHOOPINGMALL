package com.e3mall.sso.service;

import com.e3mall.pojo.TbUser;
import com.e3mall.utils.E3Result;

public interface RegisterService {
	/**
	 * 判断注册信息是否合法
	 * @param param
	 * @param type
	 * @return
	 */
	E3Result checkData(String param, int type);
	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	E3Result registUser(TbUser user);
}
