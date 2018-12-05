package cn.mingyuliu.halo.common.entity;

import cn.mingyuliu.halo.common.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * <pre>
 *     文章标签
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/09/02
 */
@Getter
@Setter
@Entity
@Table(name = "tag", indexes = {
        @Index(name = "name_index", columnList = "name"),
        @Index(name = "create_date_index", columnList = "crtDate"),
        @Index(name = "update_date_index", columnList = "updDate")})
public class Tag extends BaseEntity {

    private static final long serialVersionUID = -7501342327884372194L;

    @NotBlank(message = "标签名称不能为空")
    @Column(length = 32, columnDefinition = "VARCHAR(32) NOT NULL COMMENT '标签名称'", unique = true)
    private String name;

    @Column(columnDefinition = "VARCHAR(64) DEFAULT '' COMMENT '图标,可选'")
    private String icon;

}
