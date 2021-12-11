package manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import manager.entity.Score;
import manager.mapper.ScoreMapper;
import manager.service.IScoreService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linjinbao66
 * @since 2021-12-10
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements IScoreService {

}
