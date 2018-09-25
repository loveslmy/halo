package cn.mingyuliu.halo.model.enums;

/**
 * <pre>
 *     请求响应状态
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/03
 */
public enum ResponseStatus {

    FAIL(-100, "FAIL"),

    /**
     * 请求成功
     */
    SUCCESS(200, "OK"),

    /**
     * 资源为空
     */
    EMPTY(204, "No Content"),

    /**
     * 服务器内部错误
     */
    ERROR(500, "Internal Server Error"),

    /**
     * 未找到资源
     */
    NOT_FOUND(404, "Not Found");

    private int code;
    private String msg;

    ResponseStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
