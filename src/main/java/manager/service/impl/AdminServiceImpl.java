package manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import manager.entity.Admin;
import manager.mapper.AdminMapper;
import manager.service.IAdminService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linjinbao66
 * @since 2021-12-10
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
