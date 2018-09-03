package cn.mingyuliu.halo.service;

import cn.mingyuliu.halo.model.domain.Options;
import cn.mingyuliu.halo.model.enums.OptionEnum;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     系统选项服务接口
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/08/31
 */
public interface IOptionService {

    /**
     * 保存单个选项
     *
     * @param option {@link OptionEnum}
     * @param value  value
     */
    Options saveOption(OptionEnum option, String value);

    /**
     * 根据OptionEnum查询选项
     *
     * @param option {@link OptionEnum}
     * @return {@link Options}
     */
    Options findOneOption(OptionEnum option);

    /**
     * 查询所有选项
     *
     * @return 选项列表
     */
    List<Options> finAllOptions();

}
