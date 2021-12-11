package manager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString
@Data
@AllArgsConstructor
public class ClazzScoreVo implements Serializable {

    private String name;		//班级名称
    private double score;       //分数
    private Integer type;       //0是理论考核，1是实验考核

    private String sn;
    private String username;

    private Date createDate;    //创建日期
    private Date updateDate;    //修改者

}