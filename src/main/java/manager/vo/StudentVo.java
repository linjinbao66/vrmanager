package manager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentVo implements Serializable {
    private Long id;
    private String sn;
    private String username;
    private String password;
    private Long clazzId;
    private String clazzName;

    private String sex = "男";
    private String mobile;
    private String qq;
    private String photo;
    private Integer roleID = 1;
    private Date createDate;
    private Date updateDate;

}
