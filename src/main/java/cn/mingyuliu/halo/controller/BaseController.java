package cn.mingyuliu.halo.controller;

import cn.mingyuliu.halo.config.sys.OptionHolder;

import javax.annotation.Resource;

/**
 * <pre>
 *     Controller抽象类
 * </pre>
 *
 * @author : RYAN0UP
 * @since : 2017/12/15
 */
public abstract class BaseController {

    protected static final String ERROR_MSG = "抱歉,服务器出错了,请稍后再试!";

    @Resource
    protected OptionHolder optionHolder;

}
