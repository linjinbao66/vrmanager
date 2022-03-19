package manager.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import manager.entity.User;
import manager.service.IUserService;
import manager.util.CodeEnum;
import manager.util.SMSUtil;
import manager.util.TokenUtil;
import manager.vo.ResultVo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "系统管理")
@RestController
@CrossOrigin
@RequestMapping("/system")
public class SystemController {

    @Autowired
    IUserService userService;

    @ApiOperation(value = "登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "password", value = "密码")
    })
    @PostMapping(value = "/login")
    public ResultVo login(
            @RequestParam(value = "username")String username,
            @RequestParam(value = "password")String password
    ) throws IOException {
        if (Strings.isBlank(username) || Strings.isBlank(password)){
            return ResultVo.renderErr(CodeEnum.ERR).withRemark("用户名和密码错误");
        }
        User user = userService.getOne(new QueryWrapper<User>().eq("sn", username).eq("password", password));
        if (null == user){
            return ResultVo.renderErr().withRemark("用户名或者密码错误");
        }
        String token = TokenUtil.sign(username,password,user.getRoleId());
        if (token != null){
            Map<String,Object> map = new HashMap<>();
            user.setPassword(null);
            map.put("userinfo", user);
            map.put("token", token);
            return ResultVo.renderOk(map).withRemark("认证成功");
        }
        return ResultVo.renderErr().withRemark("认证失败");
    }


    @PostMapping("/sms")
    public ResultVo sms(@RequestParam("sn")String sn, @RequestParam("mobile") String mobile){
        if (Strings.isEmpty(sn) || Strings.isEmpty(mobile)){
            return ResultVo.renderErr().withRemark("不允许为空");
        }
        if (!Validator.isMobile(mobile)) {
            return ResultVo.renderErr().withRemark("手机号格式不对");
        }
        User user = userService.getOne(new QueryWrapper<User>().eq("sn", sn).eq("mobile", mobile));
        if (null == user){
            return ResultVo.renderErr();
        }
        String smsCode = RandomUtil.randomNumbers(6);
        SMSUtil.CODEMAP.put(sn,smsCode);

        CommonResponse commonResponse = SMSUtil.SendSMS(smsCode, mobile);
        String data = commonResponse.getData();
        JSONObject jsonObject = JSONUtil.parseObj(data);
        String code = jsonObject.getStr("Code");
        if (code.equalsIgnoreCase("OK")) {
            return ResultVo.renderOk(smsCode);
        }
        return ResultVo.renderErr(code).withRemark("工号/学号与手机号无法对应，或者不存在该用户");
    }
}