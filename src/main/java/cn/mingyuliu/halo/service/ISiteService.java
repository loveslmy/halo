package cn.mingyuliu.halo.service;

import cn.mingyuliu.halo.common.entity.Site;

import java.util.Optional;

/**
 * <pre>
 *     站点业务逻辑接口
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/11/15
 */
public interface ISiteService {

    /**
     * 新增/修改站点链接
     *
     * @param link link
     * @return Site
     */
    Site saveOrModify(Site link);

    /**
     * 根据编号查询单个链接
     *
     * @param linkId linkId
     * @return Site
     */
    Optional<Site> findByLinkId(long linkId);

}
