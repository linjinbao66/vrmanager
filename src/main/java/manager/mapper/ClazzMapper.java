package manager.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import manager.entity.Clazz;
import manager.vo.ClazzScoreVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linjinbao66
 * @since 2021-12-10
 */
public interface ClazzMapper extends BaseMapper<Clazz> {

    @Select("select clazz.name, score.score,score.type, user.sn, user.username as studentName,score.create_date as createDate, score.update_date as updateDate\n" +
            "from user left join clazz\n" +
            "on user.clazz_id = clazz.id left join score\n" +
            "on score.student_sn = user.sn\n" +
            "where user.role_id = 1 and score.score is not null")
    Page<ClazzScoreVo> getPageClazzScoreVo(Page<ClazzScoreVo> page, @Param("ew") Wrapper queryWrapper);
}
