package manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import manager.vo.ClazzScoreVo;
import org.springframework.stereotype.Service;
import manager.entity.Clazz;
import manager.mapper.ClazzMapper;
import manager.service.IClazzService;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linjinbao66
 * @since 2021-12-10
 */
@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements IClazzService {

    @Resource
    private ClazzMapper clazzMapper;

    @Override
    public IPage<ClazzScoreVo> getClazzScore(Long id, Page<ClazzScoreVo> page, QueryWrapper queryWrapper) {
        queryWrapper = new QueryWrapper<Clazz>().eq("id", id);
        return clazzMapper.getPageClazzScoreVo(page, queryWrapper);
    }
}
