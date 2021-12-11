package manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import manager.entity.User;
import manager.mapper.UserMapper;
import manager.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
