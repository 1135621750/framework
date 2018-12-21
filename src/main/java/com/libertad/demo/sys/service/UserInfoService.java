package com.libertad.demo.sys.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.libertad.demo.common.redis.RedisUtils;
import com.libertad.demo.common.startuprunner.StartupRunner;
import com.libertad.demo.common.token.BaseContext;
import com.libertad.demo.common.token.UserTokenUtils;
import com.libertad.demo.common.utils.IdWorker;
import com.libertad.demo.common.utils.ObjectUtils;
import com.libertad.demo.common.dense.SecurityKit.MD5;
import com.libertad.demo.sys.dao.UserInfoMapper;
import com.libertad.demo.sys.entity.UserInfo;


/**
* @Description: UserInfoService接口类
* @author Bai
* @date 2018/11/14 15:11
*/
@Service
public class UserInfoService{

	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private RedisUtils redisUtils;
	

	/**
	 * 登录后返回token
	 * @param userInfo 用户对象
	 * @return
	 */
	public String login(UserInfo userInfo) throws Exception {
		//加密password 
		String password = MD5.convert32(userInfo.getPassword());
		userInfo.setPassword(password);
		userInfo = userInfoMapper.login(userInfo);
		if(ObjectUtils.isNotNull(userInfo) && ObjectUtils.isNotNull(userInfo.getId())){
			//加密用户信息
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", userInfo.getId());
			jsonObject.put("name", userInfo.getUserName());
			jsonObject.put("date", new Date());
			String token = UserTokenUtils.tokenEncrypt(StartupRunner.pubKey,jsonObject );
			//在服务器端存储用户登录标识
			redisUtils.set(userInfo.getId().toString(), token);
			redisUtils.expire(userInfo.getId().toString(), 60*60*2);
			return token;
		}
		return null;
	}
	/**
	 * 退出登录
	 * @throws Exception
	 */
	public void outLogin() throws Exception{
		redisUtils.del(BaseContext.getUserId());
	}
	
}