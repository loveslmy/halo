package cn.mingyuliu.halo.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *     主题信息
 * </pre>
 *
 * @author : RYAN0UP
 * @since : 2018/1/3
 */
@Data
public class Theme implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主题名称
     */
    private String themeName;

    /**
     * 是否支持设置
     */
    private boolean hasOptions;
}
