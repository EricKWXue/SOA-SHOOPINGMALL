package com.e3mall.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.e3mall.jedis.JedisClient;
import com.e3mall.pojo.TbUser;
import com.e3mall.sso.service.TokenService;
import com.e3mall.utils.E3Result;
import com.e3mall.utils.JsonUtils;
@Service
public class TokenServiceImpl implements TokenService {
	@Autowired
	private JedisClient jedisClient;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public E3Result getUserByToken(String token) {
		String userJson = jedisClient.get("SESSION:"+token);
		if(StringUtils.isBlank(userJson)){
			return E3Result.build(400, "用户登录已经过期，请重新登录。");
		}
		jedisClient.expire("SESSION:"+token, SESSION_EXPIRE);
		TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
		return E3Result.ok(user);
	}

}
