package manager.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import manager.entity.User;
import manager.service.IUserService;
import manager.util.CodeEnum;
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
            @ApiImplicitParam(name = "password", value = "密码"),
            @ApiImplicitParam(name = "clazzNo", value = "班级编号")
    })
    @PostMapping(value = "/login")
    public ResultVo login(
            @RequestParam(value = "username")String username,
            @RequestParam(value = "password")String password,
            @RequestParam(value = "clazzNo", required = false) String clazzNo
    ) throws IOException {
        if (Strings.isBlank(username) || Strings.isBlank(password)){
            return ResultVo.renderErr(CodeEnum.ERR).withRemark("用户名和密码错误");
        }
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("sn", username).eq("password", password);
        if(Strings.isNotEmpty(clazzNo)){
            wrapper.eq("clazz_no", clazzNo);
        }
        User user = userService.getOne(wrapper);
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

}