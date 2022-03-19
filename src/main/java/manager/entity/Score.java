package manager.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author linjinbao66
 * @since 2021-12-10
 */
@ApiModel(value = "Score对象", description = "")
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String question;

    private Integer type;  //0理论，1考核

    private String studentSn;

    private String studentName;

    private Long questionid;

    private Long operationTimes;

    private String progress;

    private String remark;

    private Double score;

    private Integer studentId;

    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateDate;

    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.UPDATE)
    private String creator;

    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.UPDATE)
    private String updater;

    private String clazzNo;

    public String getClazzNo() {
        return clazzNo;
    }

    public void setClazzNo(String clazzNo) {
        this.clazzNo = clazzNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getOperationTimes() {
        return operationTimes;
    }

    public void setOperationTimes(Long operationTimes) {
        this.operationTimes = operationTimes;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStudentSn() {
        return studentSn;
    }

    public void setStudentSn(String studentSn) {
        this.studentSn = studentSn;
    }

    public Long getQuestionid() {
        return questionid;
    }

    public void setQuestionid(Long questionid) {
        this.questionid = questionid;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", type=" + type +
                ", studentSn='" + studentSn + '\'' +
                ", studentName='" + studentName + '\'' +
                ", questionid=" + questionid +
                ", operationTimes=" + operationTimes +
                ", progress='" + progress + '\'' +
                ", remark='" + remark + '\'' +
                ", score=" + score +
                ", studentId=" + studentId +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", creator='" + creator + '\'' +
                ", updater='" + updater + '\'' +
                ", clazzNo='" + clazzNo + '\'' +
                '}';
    }
}
