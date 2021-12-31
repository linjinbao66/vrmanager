package manager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;


@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClazzVo implements Serializable {
    private String username;    //负责老师姓名
    private Long id;
    private String name;		//班级名称

    private String info;		//班级信息

    private String teacherSn;		//负责老师

    private Integer function1; //实验结构功能
    private Integer function2; //实验教练功能
    private Integer function3; //实验考核功能
    
    private LocalDateTime createDate;    //创建日期
    private String creator;     //创建者
    private LocalDateTime updateDate;    //修改者
    private String updater;     //修改日期

    private String clazzNo;
}