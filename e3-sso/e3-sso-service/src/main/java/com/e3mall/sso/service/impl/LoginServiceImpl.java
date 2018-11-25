package com.e3mall.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.e3mall.jedis.JedisClient;
import com.e3mall.mapper.TbUserMapper;
import com.e3mall.pojo.TbUser;
import com.e3mall.pojo.TbUserExample;
import com.e3mall.pojo.TbUserExample.Criteria;
import com.e3mall.sso.service.LoginService;
import com.e3mall.utils.E3Result;
import com.e3mall.utils.JsonUtils;
@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public E3Result login(String username, String password) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> userList = userMapper.selectByExample(example);
		if(userList.isEmpty()){
			return E3Result.build(400, "用户名或者密码不正确");			
		}
		TbUser user = userList.get(0);
		if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
			return E3Result.build(400, "用户名或者密码不正确");
		}
		//登录成功，生成token，存入缓存
		String token = UUID.randomUUID().toString();
		jedisClient.set("SESSION:"+token, JsonUtils.objectToJson(user));
		//设置key的过期时间。模拟Session的过期时间。一般半个小时。
		jedisClient.expire("SESSION:"+token, SESSION_EXPIRE);
		return E3Result.ok(token);
	}

}
