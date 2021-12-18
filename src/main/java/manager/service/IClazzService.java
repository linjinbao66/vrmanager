package manager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import manager.entity.Clazz;
import manager.vo.ClazzScoreVo;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linjinbao66
 * @since 2021-12-10
 */
public interface IClazzService extends IService<Clazz> {

    //获取班级成绩
    IPage<ClazzScoreVo> getClazzScore(Long id, Page<ClazzScoreVo> page, QueryWrapper queryWrapper);
}
