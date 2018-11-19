package cn.mingyuliu.halo.service.impl;

import cn.mingyuliu.halo.common.entity.Site;
import cn.mingyuliu.halo.common.repository.SiteRepository;
import cn.mingyuliu.halo.service.ISiteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * <pre>
 *     站点链接业务逻辑实现类
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/11/15
 */
@Service
public class SiteServiceImpl implements ISiteService {

    @Resource
    private SiteRepository siteRepository;

    /**
     * (non-Javadoc)
     *
     * @see ISiteService#saveOrModify(Site)
     */
    @Override
    public Site saveOrModify(Site site) {
        return siteRepository.save(site);
    }

    /**
     * (non-Javadoc)
     *
     * @see ISiteService#saveOrModify(Site)
     */
    @Override
    public Optional<Site> findByLinkId(long siteId) {
        return siteRepository.findById(siteId);
    }

}
