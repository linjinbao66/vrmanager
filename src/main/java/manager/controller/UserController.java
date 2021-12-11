package manager.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import manager.entity.User;
import manager.service.IUserService;
import manager.vo.ResultVo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/")
    public ResultVo users(
            @RequestParam(value = "clazzId", required = false)Long clazzId,
            @RequestParam(value = "limit",required = false, defaultValue = "5")Long limit,
            @RequestParam(value = "page",required = false, defaultValue = "1")Long page,
            @RequestParam(value = "username", required = false)String username

    ){
        Map<String, Object> columnMap = new HashMap<>();
        if (null != clazzId){
            columnMap.put("clazz_id", clazzId);
        }
        if (Strings.isNotEmpty(username)){
            columnMap.put("username", username);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().allEq(columnMap);
        Page<User> p = new Page<>(page, limit);
        Page<User> userPage = userService.page(p, queryWrapper);

        ResultVo vo = new ResultVo();
        vo.setCount(userPage.getTotal());
        vo.setCode(0);
        vo.setData(userPage.getRecords());
        vo.setMsg("查询学生列表成功");

        return vo;

    }
}
