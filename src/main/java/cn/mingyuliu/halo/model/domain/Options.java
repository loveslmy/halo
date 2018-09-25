package cn.mingyuliu.halo.model.domain;

import cn.mingyuliu.halo.model.enums.Option;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <pre>
 *     系统设置
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2017/11/14
 */
@Getter
@Setter
@Entity
@Table(name = "halo_options")
public class Options implements Serializable {

    private static final long serialVersionUID = -4065369084341893446L;

    public Options(){

    }

    public Options(Option optionName, String optionValue) {
        this.optionName = optionName;
        this.optionValue = optionValue;
    }

    /**
     * 选项名称
     */
    @Id
    @Enumerated(EnumType.STRING)
    @Column(length = 128, columnDefinition = "VARCHAR(128) NOT NULL COMMENT '选项名称'", unique = true)
    private Option optionName;

    /**
     * 设置项的值
     */
    @Column(length = 128, columnDefinition = "VARCHAR(128) NOT NULL DEFAULT '' COMMENT '选项值'")
    private String optionValue;

}
