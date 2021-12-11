package manager.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import manager.util.CodeEnum;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class ResultVo<T> implements Serializable {
    private Integer code = CodeEnum.OK.getCode();
    private String msg = CodeEnum.OK.getMessage();
    private Date timestamp = new Date();
    private T data;
    private Long ttl;
    private Long count;

    public ResultVo(CodeEnum code) {
        this.setCode(code.getCode());
        this.setMsg(code.getMessage());
    }

    public ResultVo(T data) {
        CodeEnum code = CodeEnum.OK;
        this.setCode(code.getCode());
        this.setMsg(code.getMessage());
        this.setData(data);
        this.setCount(count);
    }

    @JsonIgnore
    public boolean isOk() {
        return CodeEnum.OK.getCode().equals(this.code);
    }

    /**
     * 附加信息
     *
     * @param remark
     * @return
     */
    public ResultVo withRemark(String remark) {
        this.setMsg(this.getMsg() + "(" + remark + ")");
        return this;
    }

    public ResultVo withCount(Long count) {
        this.setCount(count);
        return this;
    }

    public ResultVo withTTL(Long ttl){
        this.setTtl(ttl);
        return this;
    }

    /**
     * 构建失败返回结果
     *
     * @param code 状态码
     * @return 失败返回结果
     */
    public static ResultVo renderErr(CodeEnum code) {
        return new ResultVo(code);
    }
    public static ResultVo renderErr() {
        return new ResultVo(CodeEnum.ERR);
    }

    /**
     * 构建无效操作返回结果
     *
     * @return
     */
    public static ResultVo renderVain() {
        return new ResultVo(CodeEnum.INVALID_OPERATION);
    }

    /**
     * 构建成功返回结果
     *
     * @return
     */
    public static ResultVo renderOk() {
        return new ResultVo(CodeEnum.OK);
    }

    /**
     * 构建成功返回结果(带数据)
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultVo<T> renderOk(T data) {
        return new ResultVo<>(data);
    }

    public static <T> ResultVo<T> renderErr(T data){
        return new ResultVo<>(data);
    }
}