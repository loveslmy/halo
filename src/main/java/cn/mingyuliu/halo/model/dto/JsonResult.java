package cn.mingyuliu.halo.model.dto;

import cn.mingyuliu.halo.model.enums.ResponseStatus;
import lombok.Getter;

/**
 * <pre>
 *     Json响应
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/02
 */
@Getter
public class JsonResult<T> {

    public static final JsonResult EMPTY = new JsonResult<>(ResponseStatus.EMPTY);

    /**
     * 返回的状态码，0：失败，1：成功
     */
    private ResponseStatus status;

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
    public JsonResult(ResponseStatus status, String msg) {
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
    public JsonResult(ResponseStatus status, String msg, T result) {
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
    public JsonResult(ResponseStatus status, T result) {
        this.status = status;
        this.msg = status.getMsg();
        this.datas = result;
    }

    /**
     * 返回状态码
     *
     * @param status 状态
     */
    public JsonResult(ResponseStatus status) {
        this.status = status;
        this.msg = status.getMsg();
    }

}
