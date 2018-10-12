package cn.mingyuliu.halo.config.sys;

import cn.mingyuliu.halo.common.entity.Options;
import cn.mingyuliu.halo.common.enums.Option;
import cn.mingyuliu.halo.service.IOptionService;
import cn.mingyuliu.halo.utils.Int2ObjectLockMap;
import cn.mingyuliu.halo.utils.VariableHelper;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     选项管理组件
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/02
 */
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class OptionHolder {

    /**
     * 所有设置选项（name,value）
     */
    @Getter
    private Int2ObjectMap<String> options = new Int2ObjectLockMap<>(Option.values().length);

    @Setter
    @Resource
    private IOptionService optionService;

    /**
     * 加载所有设置选项
     */
    @PostConstruct
    public void loadOptions() {
        List<Options> optionList = optionService.finAllOptions();
        if (CollectionUtils.isNotEmpty(optionList)) {
            options.clear();
            optionList.forEach(option -> this.options.put(option.getOptionName().getCode(), option.getOptionValue()));
        }
    }

    /**
     * 保存多个选项
     *
     * @param optionMap key optionEnum value optionValue
     */
    public void saveOptions(Map<String, String> optionMap) {
        if (null == optionMap || optionMap.isEmpty()) {
            return;
        }
        for (Map.Entry<String, String> entry : optionMap.entrySet()) {
            set(Option.valueOf(entry.getKey()), entry.getValue());
        }
    }

    /**
     * 根据选项获取选项值
     *
     * @param option {@link Option}
     * @return 选项值
     */
    public boolean getBoolean(Option option) {
        return VariableHelper.parseBoolean(get(option));
    }

    /**
     * 根据选项获取选项值
     *
     * @param option {@link Option}
     * @return 选项值
     */
    public byte getByte(Option option) {
        return VariableHelper.parseByte(get(option));
    }

    /**
     * 根据选项获取选项值
     *
     * @param option {@link Option}
     * @return 选项值
     */
    public String get(Option option) {
        if (options.containsKey(option.getCode())) {
            return options.get(option.getCode());
        }
        String value = optionService.findOneOption(option).getOptionValue();
        options.put(option.getCode(), value);
        return value;
    }

    /**
     * 设置选项值
     *
     * @param option   {@link Option}
     * @param newValue 新选项值
     */
    public Options set(Option option, String newValue) {
        Options op = optionService.saveOption(option, newValue);
        options.put(option.getCode(), newValue);
        return op;
    }

}
