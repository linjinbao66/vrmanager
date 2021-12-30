package manager.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel(value = "用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String mobile;

    private String password;

    private String photo;

    private String qq;

    private Integer sex;    //0是男 1是女

    private String sn;

    private String username;

    private Long clazzId;

    private Long clazzNo;       //班级编号

    private Integer roleId;     //0管理员 1学生 2教师

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getClazzId() {
        return clazzId;
    }

    public void setClazzId(Long clazzId) {
        this.clazzId = clazzId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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

    public Long getClazzNo() {
        return clazzNo;
    }

    public void setClazzNo(Long clazzNo) {
        this.clazzNo = clazzNo;
    }

    @Override
    public String toString() {
        return "User [clazzId=" + clazzId + ", clazzNo=" + clazzNo + ", createDate=" + createDate + ", creator="
                + creator + ", id=" + id + ", mobile=" + mobile + ", password=" + password + ", photo=" + photo
                + ", qq=" + qq + ", roleId=" + roleId + ", sex=" + sex + ", sn=" + sn + ", updateDate=" + updateDate
                + ", updater=" + updater + ", username=" + username + "]";
    }
    
}
