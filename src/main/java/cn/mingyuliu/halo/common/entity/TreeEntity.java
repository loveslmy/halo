package cn.mingyuliu.halo.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * <pre>
 *     树形结构基类
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/10/22
 */
@MappedSuperclass
@Getter
@Setter
public abstract class TreeEntity extends BaseEntity {

    @Column(columnDefinition = "BIGINT(20) DEFAULT NULL COMMENT '上级id'")
    private Long parentId;

    @Column(columnDefinition = "SMALLINT(6) NOT NULL DEFAULT '0' COMMENT '序号'")
    private short orderSeq;

    @Column(columnDefinition = "VARCHAR(64) DEFAULT '' COMMENT '图标,可选'")
    private String icon;

    @Column(columnDefinition = "BIT NOT NULL DEFAULT b'1' COMMENT '是否展开'")
    private boolean expanded;

}
