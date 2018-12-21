package com.libertad.demo.sys.controller;

import com.libertad.demo.common.exceptions.BusiException;
import com.libertad.demo.common.result.JsonResult;
import com.libertad.demo.common.result.R;
import com.libertad.demo.common.token.IgnoreUserToken;
import com.libertad.demo.common.utils.ObjectUtils;
import com.libertad.demo.sys.entity.UserInfo;
import com.libertad.demo.sys.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* @Description: 登录
* @author Bai
* @date 2018/11/14 15:11
*/
@Api(tags = { "用户接口" })
@RestController
@CrossOrigin
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    
    
    /**
	 * 登录
	 * @return
	 */
	@IgnoreUserToken
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(value = "用户登录")
	public JsonResult<?> login(@RequestBody UserInfo userInfo) throws Exception {
		//查询数据
		String token = userInfoService.login(userInfo);
		if(ObjectUtils.isNotNull(token)){
			return JsonResult.success(token);
		}
		return JsonResult.error(R.LONGIN_ERROR);
	}
	/**
	 * 退出登录
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/outLogin", method = RequestMethod.POST)
	@ApiOperation(value = "退出登录")
	public JsonResult<?> outLogin() throws Exception {
		try {
			userInfoService.outLogin();
		} catch (Exception e) {
			throw new BusiException(R.FAILURE);
		}
		return JsonResult.success();
	}
    
}