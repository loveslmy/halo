package cn.mingyuliu.halo.common.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * <pre>
 *     Json响应
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/09/02
 */
@Getter
@Setter
public class JsonResult<T> {

    public JsonResult() {
    }

    /**
     * {@link HttpStatus}
     */
    private HttpStatus status;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回的数据
     */
    private T datas;

    /**
     * 不返回数据的构造方法
     *
     * @param status 状态
     * @param msg    信息
     */
    public JsonResult(HttpStatus status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    /**
     * 返回数据的构造方法
     *
     * @param status 状态
     * @param msg    信息
     * @param result 数据
     */
    public JsonResult(HttpStatus status, String msg, T result) {
        this.status = status;
        this.msg = msg;
        this.datas = result;
    }

    /**
     * 返回状态码和数据
     *
     * @param status 状态
     * @param result 数据
     */
    public JsonResult(HttpStatus status, T result) {
        this.status = status;
        this.msg = status.name();
        this.datas = result;
    }

    /**
     * 返回状态码
     *
     * @param status 状态
     */
    public JsonResult(HttpStatus status) {
        this.status = status;
        this.msg = status.name();
    }

}
