package cn.mingyuliu.halo.repository;

import cn.mingyuliu.halo.model.domain.Options;
import cn.mingyuliu.halo.model.enums.OptionEnum;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 *     系统设置持久层
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2017/11/14
 */
public interface OptionsRepository extends JpaRepository<Options, Long> {

    /**
     * 根据name查询单个option
     *
     * @param option {@link OptionEnum}
     * @return Options
     */
    Options findOptionsByOptionName(OptionEnum option);
}
