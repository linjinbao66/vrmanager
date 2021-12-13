package manager.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ScoreVo implements Serializable{

    private Long id;
    
    private String question;

    private Long questionid;

    private Double score;

    private Integer type;

    private String studentSn;

    private LocalDateTime createDate;


}
