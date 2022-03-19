package manager.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import manager.entity.User;
import manager.service.IUserService;
import manager.util.BizException;
import manager.util.CodeEnum;
import manager.vo.ResultVo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/")
    public ResultVo users(
            @RequestParam(value = "clazzId", required = false)Long clazzId,
            @RequestParam(value = "clazzNo", required = false)String clazzNo,
            @RequestParam(value = "limit",required = false, defaultValue = "5")Long limit,
            @RequestParam(value = "page",required = false, defaultValue = "1")Long page,
            @RequestParam(value = "username", required = false)String username,
            @RequestParam(value = "roleId", required = false)Long roleId
    ){
        Map<String, Object> columnMap = new HashMap<>();
        if (null != clazzId){
            columnMap.put("clazz_id", clazzId);
        }
        if (null != clazzNo){
            columnMap.put("clazz_no", clazzNo);
        }
        if (null != roleId){
            columnMap.put("role_id", roleId);
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
        vo.setMsg("查询列表成功");

        return vo;
    }

    @PostMapping("/")
    public ResultVo addOne(User user) {

        user = validateUser(user);
        User bysn = userService.getOne(new QueryWrapper<User>().eq("sn", user.getSn()).eq("clazz_no",user.getClazzNo()));
        if (null != bysn){
            return ResultVo.renderErr().withRemark("学号和课程不唯一");
        }
        boolean save = userService.save(user);
        return save ? ResultVo.renderOk().withRemark("添加成功") : ResultVo.renderErr().withRemark("添加失败");
    }

    @PutMapping("/")
    public ResultVo updateOne(User user) {
        user = validateUser(user);
        User bysn = userService.getOne(new QueryWrapper<User>().eq("sn", user.getSn()).eq("clazz_no",user.getClazzNo()));
        if (null == bysn){
            return ResultVo.renderErr().withRemark("学号或课程不唯一");
        }
        boolean b = userService.updateById(user);
        return b ? ResultVo.renderOk().withRemark("修改成功") : ResultVo.renderErr().withRemark("修改失败");
    }

    @DeleteMapping("/{id}")
    public ResultVo deleteOne(@PathVariable(value = "id")Long id){
        User byId = userService.getById(id);
        if (null == byId){
            return ResultVo.renderErr().withRemark("删除失败，记录不存在");
        }
        boolean b = userService.removeById(id);
        return b ? ResultVo.renderOk().withRemark("删除成功") : ResultVo.renderErr().withRemark("删除失败");
    }

    @PostMapping("/delBatch")
    public ResultVo deleteBatch(@RequestParam(value = "ids[]") Long[] ids){
        boolean b = userService.removeByIds(Arrays.asList(ids));
        return b ? ResultVo.renderOk().withRemark("删除成功") : ResultVo.renderErr().withRemark("删除失败");
    }

    @ApiOperation(value = "从excel批量导入")
    @PostMapping("/importBatch")
    public ResultVo importBatch(
            @RequestParam("file") MultipartFile importBatch) throws Exception {
        ExcelReader reader = ExcelUtil.getReader(importBatch.getInputStream());
        List<User> userList = reader.readAll(User.class);

        if(CollectionUtil.isEmpty(userList)){
            return ResultVo.renderErr().withRemark("导入数据为空");
        }

        for (int i=0; i<userList.size(); i++){
            User tmpUser = userList.get(i);
            validateUser(tmpUser);
        }

        boolean b = userService.saveBatch(userList);
        return b ? ResultVo.renderOk().withRemark("导入成功") : ResultVo.renderErr().withRemark("导入失败");
    }

    public User validateUser(User user){
        if (Strings.isEmpty(user.getSn())||Strings.isEmpty(user.getUsername())){
            throw new BizException(CodeEnum.ERR).withRemark("学号/用户名必填");
        }

        if(user.getUsername().equalsIgnoreCase("admin") || user.getSn().equalsIgnoreCase("admin")){
            user.setUsername("nnn");
            user.setSn("nnn");
            throw new BizException(CodeEnum.ERR).withRemark("用户名非法");
        }

        if (!Validator.isGeneral(user.getSn())){
            throw new BizException(CodeEnum.ERR).withRemark("学号格式错误");
        }
        if (null == user.getSex()||(user.getSex()!=0)&& user.getSex() !=1){
            user.setSex(0);
        }
        if (!Validator.isMobile(user.getMobile())) {
            throw new BizException(CodeEnum.ERR).withRemark("手机号格式验证不通过");
        }
        if (Strings.isEmpty(user.getPassword())){
            user.setPassword("123456");
        }
        if (null == user.getRoleId() || (user.getRoleId()!=0 && user.getRoleId() !=1 &&user.getRoleId()!=2)){
            user.setRoleId(1);
        }
        return user;
    }
}