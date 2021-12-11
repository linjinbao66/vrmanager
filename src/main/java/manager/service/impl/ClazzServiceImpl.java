package manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import manager.entity.Clazz;
import manager.mapper.ClazzMapper;
import manager.service.IClazzService;

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

}
