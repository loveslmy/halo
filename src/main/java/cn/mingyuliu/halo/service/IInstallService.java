package cn.mingyuliu.halo.service;

import cn.mingyuliu.halo.model.dto.InstallDto;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 *     安装服务接口
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/06
 */
public interface IInstallService {

    /**
     * 安装博客
     *
     * @param installDto {@link InstallDto}
     */
    void install(InstallDto installDto, HttpServletRequest request);


}
