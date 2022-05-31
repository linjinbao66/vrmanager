package manager.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import manager.entity.Clazz;
import manager.entity.Score;
import manager.entity.User;
import manager.service.IClazzService;
import manager.service.IScoreService;
import manager.service.IUserService;
import manager.util.BizException;
import manager.util.CodeEnum;
import manager.vo.ClazzScoreVo2;
import manager.vo.ClazzVo;
import manager.vo.ResultVo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author linjinbao66
 * @since 2021-12-10
 */
@RestController
@Slf4j
@RequestMapping("/clazz")
public class ClazzController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    IClazzService clazzService;

    @Autowired
    IUserService userService;

    @Autowired
    IScoreService scoreService;

    @ApiOperation(value = "获取班级列表")
    @GetMapping("/")
    public ResultVo<Object> clazzList(
            @RequestParam(value = "name", required = false)String name,
            @RequestParam(value = "page",required = false, defaultValue = "1")Integer pageNum,
            @RequestParam(value = "limit",required = false, defaultValue = "5")Integer pageSize
    ){
        Map<String, Object> columnMap = new HashMap<>();
        if (null != name){
            columnMap.put("name", name);
        }
        Integer roleId = (Integer) request.getAttribute("roleId");
        log.info("roleId = {}", roleId);
        if (null == roleId || 0==roleId){

        }else if (2==roleId){
            //教师权限
            String sn = (String) request.getAttribute("sn");
            if (Strings.isNotEmpty(sn)){
                columnMap.put("teacher_sn", sn);
            }
        }else if (1==roleId){
            // throw new BizException(CodeEnum.ACCESS_DENIED);
        }
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<Clazz>().allEq(columnMap);
        Page<Clazz> p = new Page<>(pageNum, pageSize);
        Page<Clazz> clazzpage = clazzService.page(p, queryWrapper);
        List<User> teachUsers = userService.list(new QueryWrapper<User>().eq("role_id", 2));
        List<ClazzVo> clazzVos = new ArrayList<>();
        for(Clazz clazz : clazzpage.getRecords()){
            ClazzVo clazzVo = new ClazzVo();
            clazzVo.setId(clazz.getId());
            clazzVo.setName(clazz.getName());
            clazzVo.setUsername(teachUsers.stream().filter(user -> user.getId().equals(clazz.getTeacherId())||user.getSn().equals(clazz.getTeacherSn())).findAny().orElse(new User()).getUsername());
            clazzVo.setCreateDate(clazz.getCreateDate());
            clazzVo.setFunction1(clazz.getFunction1());
            clazzVo.setFunction2(clazz.getFunction2());
            clazzVo.setFunction3(clazz.getFunction3());
            clazzVo.setInfo(clazz.getInfo());
            clazzVo.setClazzNo(clazz.getClazzNo());
            clazzVos.add(clazzVo);
        }
        ResultVo<Object> vo = new ResultVo<>();
        vo.setCount(clazzpage.getTotal());
        vo.setCode(0);
        vo.setData(clazzVos);
        vo.setMsg("查询班级列表成功");
        return vo;
    }

    @ApiOperation(value = "新增班级")
    @PostMapping("/")
    public ResultVo<CodeEnum> addOne(Clazz clazz){
        validateClazz(clazz);
        
        if (StrUtil.isNotEmpty(clazz.getTeacherSn())){
            User one = userService.getOne(new QueryWrapper<User>().eq("sn", clazz.getTeacherSn())
                    .eq("role_id", "2"));
            if (null == one){
                return ResultVo.renderErr().withRemark("教师不存在");
            }
            clazz.setTeacherId(one.getId());
        }

        boolean save = clazzService.save(clazz);
        return save ? ResultVo.renderOk().withRemark("新增班级成功"):ResultVo.renderErr().withRemark("新增班级失败");
    }

    @ApiOperation(value = "修改班级")
    @PutMapping("/")
    public ResultVo<CodeEnum> updateOne(@RequestBody Clazz clazz){
        validateClazz(clazz);
        if (StrUtil.isNotEmpty(clazz.getTeacherSn())){
            User one = userService.getOne(new QueryWrapper<User>().eq("sn", clazz.getTeacherSn())
                    .eq("role_id", "2"));
            if (null == one){
                return ResultVo.renderErr().withRemark("教师不存在");
            }
            clazz.setTeacherId(one.getId());
        }
        boolean update = clazzService.updateById(clazz);

        return update ? ResultVo.renderOk().withRemark("更新班级成功"):ResultVo.renderErr().withRemark("更新班级失败");
    }

    @ApiOperation(value = "删除班级")
    @DeleteMapping("/{id}")
    public ResultVo<CodeEnum> deleteOne(@PathVariable(value = "id")Long id){
        Clazz byId = clazzService.getById(id);
        if (null == byId){
            return ResultVo.renderErr().withRemark("删除失败，记录不存在");
        }
        boolean b = clazzService.removeById(id);
        return b ? ResultVo.renderOk().withRemark("删除成功") : ResultVo.renderErr().withRemark("删除失败");
    }

    @ApiOperation(value = "批量删除班级")
    @ApiImplicitParam(name = "ids", value = "id数组", example = "[1 2 3]")
    @PostMapping("/delBatch")
    public ResultVo<CodeEnum> deleteBatch(@RequestParam(value = "ids[]") Long[] ids){
        boolean b = clazzService.removeByIds(Arrays.asList(ids));
        return b ? ResultVo.renderOk().withRemark("删除成功") : ResultVo.renderErr().withRemark("删除失败");
    }

    @ApiOperation(value = "从excel批量导入")
    @PostMapping("/importBatch")
    public ResultVo<CodeEnum> importBatch(@RequestParam("file") MultipartFile importBatch) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(importBatch.getInputStream());
        List<Clazz> clazzList = reader.readAll(Clazz.class);

        if(CollUtil.isEmpty(clazzList)){
            return ResultVo.renderErr().withRemark("导入数据为空");
        }

        for (int i=0; i<clazzList.size(); i++){
            Clazz tmpClazz = clazzList.get(i);
            validateClazz(tmpClazz);
        }

        boolean b = clazzService.saveBatch(clazzList);
        return b ? ResultVo.renderOk().withRemark("导入成功") : ResultVo.renderErr().withRemark("导入失败");
    }

    //1. 查询出班级的所有学生
    //2. 查询每个学生成绩
    @ApiOperation(value = "获取班级下的所有学生成绩")
    @GetMapping("/score")
    public ResultVo score(
            @RequestParam(value = "clazzId", required = false)Long clazzId,
            @RequestParam(value = "clazzNo", required = false) String clazzNo,
            @RequestParam(value = "page", required = false, defaultValue = "1")Long page,
            @RequestParam(value = "limit", required = false, defaultValue = "50")Long limit
    ){
        List<ClazzScoreVo2> vo2List = new ArrayList<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        if(clazzId != null){
            queryWrapper = queryWrapper.eq("clazz_id", clazzId);
        }
        if(StrUtil.isNotEmpty(clazzNo)){
            queryWrapper = queryWrapper.eq("clazz_no", clazzNo);
        }
        Page<User> p = new Page<>(page, limit);
        Page<User> userPage = userService.page(p,queryWrapper.eq("role_id", 1));//获取班级下所有学生
        List<User> studeList = userPage.getRecords();
        Clazz clazz = clazzService.getOne(new QueryWrapper<Clazz>().eq("id", clazzId).or().eq("clazz_no", clazzNo));//获取班级
        for(User student : studeList){
            //遍历学生
            List<Score> scores = scoreService.list(new QueryWrapper<Score>().eq("student_sn", student.getSn()).isNotNull("questionid"));
            ClazzScoreVo2 vo2 = new ClazzScoreVo2();
            if(CollUtil.isEmpty(scores)) {
                //学生没有成绩情况也要返回值
                vo2.setScore0(0);
                vo2.setScore1(0);
                vo2.setStudentName(student.getUsername());
                vo2.setOperationTimes(0l);
                vo2.setSn(student.getSn());
                vo2.setName(null == clazz ? null : clazz.getName());
            }else {
                long maxQuestionIdScore0 = scores.stream().filter(score -> score.getType()==0).mapToLong(Score::getQuestionid).max().orElse(0l);//理论成绩的最大值
                List<Score> scores0 = scores.stream().filter(score -> score.getQuestionid().equals(maxQuestionIdScore0))
                        .collect(Collectors.toList());
                if(0 == maxQuestionIdScore0) continue;
                vo2.setSn(student.getSn());
                vo2.setName(null == clazz ? null : clazz.getName());
                if(CollUtil.isNotEmpty(scores0)){
                    vo2.setCreateDate(scores0.get(0).getCreateDate());
                }
                double score0 = scores0.stream().filter(score->score.getType().equals(0)).mapToDouble(Score::getScore).sum();//理论总成绩

                long maxQuestionIdScore1 = scores.stream().filter(score -> score.getType()==1).mapToLong(Score::getQuestionid).max().orElse(0l);//实操成绩最大值

                List<Score> scores1 = scores.stream().filter(score -> score.getQuestionid().equals(maxQuestionIdScore1)).collect(Collectors.toList());
                double score1 = scores1.stream().filter(score->score.getType().equals(1)).mapToDouble(Score::getScore).sum();//实操总成绩
                vo2.setScore0(score0);
                vo2.setScore1(score1);
                vo2.setStudentName(student.getUsername());
                long operationTimes = scores.stream().collect(Collectors.groupingBy(Score::getOperationTimes)).size();
                vo2.setOperationTimes(operationTimes);
            }
            vo2.setClazzNo(clazzNo);
            vo2List.add(vo2);
        }
        // Collections.sort(vo2List, c);
        ResultVo<Object> vo = new ResultVo<>();
        vo.setCode(0);
        vo.setData(vo2List);
        vo.setCount(userPage.getTotal());
        vo.setMsg("查询班级成绩成功");
        return vo;
    }

    private void validateClazz(Clazz clazz) {
        if(StrUtil.isEmpty(clazz.getClazzNo())){
            throw new BizException(CodeEnum.ERR).withRemark("班级编号必填");
        }
        if (StrUtil.isEmpty(clazz.getName())){
            throw new BizException(CodeEnum.ERR).withRemark("班级名称为空");
        }
        if (null == clazz.getFunction1()){
            clazz.setFunction1(0);
        }
        if (clazz.getFunction1()!=0 && clazz.getFunction1()!=1){
            clazz.setFunction1(0);
        }
        if (null == clazz.getFunction2()){
            clazz.setFunction2(0);
        }
        if (clazz.getFunction2()!=0 && clazz.getFunction2()!=1){
            clazz.setFunction2(0);
        }
        if (null == clazz.getFunction3()){
            clazz.setFunction3(0);
        }
        if (clazz.getFunction3()!=0 && clazz.getFunction3()!=1){
            clazz.setFunction3(0);
        }
    }
}

