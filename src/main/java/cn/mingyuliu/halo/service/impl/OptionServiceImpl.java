package cn.mingyuliu.halo.service.impl;

import cn.mingyuliu.halo.model.domain.Options;
import cn.mingyuliu.halo.model.enums.Option;
import cn.mingyuliu.halo.repository.OptionsRepository;
import cn.mingyuliu.halo.service.IOptionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 *     系统选项服务实现类
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/08/31
 */
@Service
public class OptionServiceImpl implements IOptionService {

    @Resource
    private OptionsRepository optionsRepository;

    @Override
    public Options saveOption(Option option, String value) {
        Options dbOption = optionsRepository.findOptionsByOptionName(option);
        // 如果查询到有该设置选项则做更新操作，反之保存新的设置选项
        if (null == dbOption) {
            dbOption = new Options();
            dbOption.setOptionName(option);
        }
        dbOption.setOptionValue(value);
        return optionsRepository.save(dbOption);
    }

    @Override
    public Options findOneOption(Option option) {
        Options dbOption = optionsRepository.findOptionsByOptionName(option);
        if (null != dbOption) {
            return dbOption;
        }
        return saveOption(option,StringUtils.EMPTY);
    }

    @Override
    public List<Options> finAllOptions() {
        return optionsRepository.findAll();
    }

}
