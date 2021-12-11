package manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import manager.entity.Student;
import manager.mapper.StudentMapper;
import manager.service.IStudentService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linjinbao66
 * @since 2021-12-10
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
