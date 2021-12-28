package manager.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import manager.entity.Score;
import manager.entity.User;
import manager.service.IScoreService;
import manager.service.IUserService;
import manager.util.CodeEnum;
import manager.vo.ResultVo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author linjinbao66
 * @since 2021-12-10
 */
@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    IScoreService scoreService;

    @Autowired
    IUserService userService;

    @ApiOperation(value = "查询成绩列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sn", value = "学号"),
            @ApiImplicitParam(name = "type", value = "类型"),
            @ApiImplicitParam(name = "question", value = "问题"),
            @ApiImplicitParam(name = "page", value = "页码", example = "1", defaultValue = "1"),
            @ApiImplicitParam(name = "limit", value = "分页大小", example = "5", defaultValue = "5")
    })
    @GetMapping("/")
    public ResultVo<Object> scoreList(
            @RequestParam(value = "sn", required = false) String sn,
            @RequestParam(value = "type", required = false) Long type,
            @RequestParam(value = "question", required = false) String question,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "limit", required = false, defaultValue = "5") Integer pageSize) {
        Map<String, Object> columnMap = new HashMap<>();
        if (Strings.isNotEmpty(sn)) {
            columnMap.put("student_sn", sn);
        }
        if (null != type) {
            columnMap.put("type", type);
        }
        if (Strings.isNotEmpty(question)) {
            columnMap.put("question", question);
        }

        QueryWrapper<Score> queryWrapper = new QueryWrapper<Score>().allEq(columnMap);
        Page<Score> p = new Page<>(pageNum, pageSize);
        Page<Score> scorePage = scoreService.page(p, queryWrapper);

        User bysn = userService.getOne(new QueryWrapper<User>().eq("sn", sn));
        List<Score> records = scorePage.getRecords();
        if (bysn != null) {
            records.forEach(score -> score.setStudentName(bysn.getUsername()));
        }

        ResultVo<Object> vo = new ResultVo<>();
        vo.setCount(scorePage.getTotal());
        vo.setCode(0);
        vo.setData(records);
        vo.setMsg("查询成绩成功");
        return vo;
    }

    @GetMapping("/studentScore")
    public ResultVo studentScore(@RequestParam(value = "sn") String sn, @RequestParam(value = "type") Long type) {

        User stu = userService.getOne(new QueryWrapper<User>().eq("role_id", 1).eq("sn", sn));
        if (null == stu)
            return ResultVo.renderErr().withRemark("学生不存在");

        List<Score> scores = scoreService.list(new QueryWrapper<Score>()
                .eq("student_sn", sn)
                .eq("type", type)
                .isNotNull("questionid"));
        if (CollectionUtil.isEmpty(scores))
            return ResultVo.renderErr().withRemark("无法查询到该学生成绩");
        Long maxQuestionId = scores.stream().mapToLong(Score::getQuestionid).max().getAsLong();

        List<Score> s = scores.stream().filter(score -> score.getQuestionid().equals(maxQuestionId))
                .collect(Collectors.toList());

        for (int i = 0; i < s.size(); i++) {
            s.get(i).setId((long) i + 1);
            s.get(i).setStudentName(stu.getUsername());
        }

        ResultVo vo = new ResultVo();
        vo.setCount((long) s.size());
        vo.setCode(0);
        vo.setData(s);
        vo.setMsg("查询成绩成功");
        return vo;
    }

    @ApiOperation("新增成绩")
    @PostMapping("/")
    public ResultVo<CodeEnum> addClazz(Score score) {

        if (score.getType() != 1 && score.getType() != 0) {
            return ResultVo.renderErr().withRemark("类型可选值为0理论，1考核");
        }

        User bySn = userService.getOne(new QueryWrapper<User>().eq("sn", score.getStudentSn()));
        if (null == bySn) {
            return ResultVo.renderErr().withRemark("学生不存在");
        }

        boolean save = scoreService.save(score);
        return save ? ResultVo.renderOk().withRemark("新增成绩成功") : ResultVo.renderErr().withRemark("新增失败");
    }

    @ApiOperation("批量添加成绩")
    @PostMapping(value = "/batchAdd")
    public @ResponseBody ResultVo<CodeEnum> batchAdd(@RequestBody List<Score> scores) {
        for (Score score : scores) {
            if (Strings.isEmpty(score.getQuestion()) || null == score.getOperationTimes()
                    || (null == score.getStudentSn()) || (null == score.getType())) {
                scores.remove(score);
            }
        }
        boolean b = scoreService.saveBatch(scores);
        return b ? ResultVo.renderOk().withRemark("批量添加成绩成功！") : ResultVo.renderErr().withRemark("批量新增成绩失败！");
    }

    @ApiOperation("更新成绩")
    @PostMapping("/updateScore")
    public ResultVo<CodeEnum> updateScore(Score score) {

        if (Strings.isEmpty(score.getStudentSn())) {
            return ResultVo.renderErr().withRemark("请关联学生学号");
        }
        User bySn = userService.getOne(new QueryWrapper<User>().eq("sn", score.getStudentSn()));
        if (null == bySn) {
            return ResultVo.renderErr().withRemark("学生不存在");
        }
        boolean b = scoreService.saveOrUpdate(score, new QueryWrapper<Score>()
                .eq("questionid", score.getQuestionid())
                .eq("student_sn", score.getStudentSn())
                .eq("type", score.getType()));
        return b ? ResultVo.renderOk().withRemark("更新成绩成功") : ResultVo.renderErr().withRemark("更新成绩失败");
    }

    @ApiOperation("批量删除")
    @PostMapping("/delBatch")
    public ResultVo deleteScore(@RequestParam(value = "ids[]") Long[] ids) {
        boolean b = scoreService.removeByIds(Arrays.asList(ids));
        return b ? ResultVo.renderOk(ids).withRemark("删除成功") : ResultVo.renderErr().withRemark("删除失败");
    }

    @ApiOperation(value = "从excel导入")
    @PostMapping("/importBatch")
    public ResultVo importBatch(@RequestParam("file") MultipartFile importBatch)  {
        ExcelReader reader = null;
        List<Score> scoreList = new ArrayList<>();
        try {
            reader = ExcelUtil.getReader(importBatch.getInputStream());
            scoreList = reader.readAll(Score.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (CollUtil.isEmpty(scoreList)) {
            return ResultVo.renderErr().withRemark("导入数据为空");
        }
        for (Score score : scoreList) {
            if (Strings.isEmpty(score.getStudentSn())) {
                return ResultVo.renderErr(score).withRemark("请关联学生学号");
            }
        }
        boolean b = scoreService.saveBatch(scoreList);
        return b ? ResultVo.renderOk().withRemark("导入成绩成功！") : ResultVo.renderErr().withRemark("导入失败！");
    }
}
