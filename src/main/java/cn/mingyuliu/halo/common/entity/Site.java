package cn.mingyuliu.halo.common.entity;

import cn.mingyuliu.halo.common.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * <pre>
 *     站点链接
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/11/09
 */
@Entity
@Getter
@Setter
@Table(name = "site", indexes = {@Index(name = "name_index", columnList = "name"),
        @Index(name = "category_id_index", columnList = "categoryId"),
        @Index(name = "create_date_index", columnList = "crtDate"),
        @Index(name = "update_date_index", columnList = "updDate")})
public class Site extends BaseEntity {

    private static final long serialVersionUID = 5441686055841177588L;

    @Column(columnDefinition = "BIGINT COMMENT '图片,引用文件ID'")
    private Long refId;

    @Column(columnDefinition = "BIGINT NOT NULL COMMENT '分类ID'")
    private long categoryId;

    @Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '站点名称'")
    private String name;

    @Column(columnDefinition = "VARCHAR(128) NOT NULL COMMENT '站点地址'",unique = true)
    private String url;

    @Column(columnDefinition = "VARCHAR(128) COMMENT '站点描述'")
    private String description;

    @Transient
    private File file;

}
