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
@ApiModel(value = "Clazz对象", description = "")
public class Clazz implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer function1;

    private Integer function2;

    private Integer function3;

    private String info;

    private String name;

    private Long teacherId;

    private String teacherSn;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFunction1() {
        return function1;
    }

    public void setFunction1(Integer function1) {
        this.function1 = function1;
    }

    public Integer getFunction2() {
        return function2;
    }

    public void setFunction2(Integer function2) {
        this.function2 = function2;
    }

    public Integer getFunction3() {
        return function3;
    }

    public void setFunction3(Integer function3) {
        this.function3 = function3;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
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

    public String getTeacherSn() {
        return teacherSn;
    }

    public void setTeacherSn(String teacherSn) {
        this.teacherSn = teacherSn;
    }

    @Override
    public String toString() {
        return "Clazz{" +
        "id=" + id +
        ", createDate=" + createDate +
        ", creator=" + creator +
        ", function1=" + function1 +
        ", function2=" + function2 +
        ", function3=" + function3 +
        ", info=" + info +
        ", name=" + name +
        ", teacherId=" + teacherId +
        ", updateDate=" + updateDate +
        ", updater=" + updater +
        ", teacherSn=" + teacherSn +
        "}";
    }
}
