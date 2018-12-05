package cn.mingyuliu.halo.common.entity.base;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *     树形结构基类
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/10/22
 */
@MappedSuperclass
@Getter
@Setter
public abstract class TreeEntity extends BaseEntity {

    @Column(columnDefinition = "BIGINT(20) DEFAULT NULL COMMENT '上级id'")
    private Long parentId;

    @Column(columnDefinition = "TINYINT(6) NOT NULL DEFAULT '0' COMMENT '序号'")
    private byte orderSeq;

    @Column(columnDefinition = "VARCHAR(64) DEFAULT '' COMMENT '图标,可选'")
    private String icon;

    @Column(columnDefinition = "BIT NOT NULL DEFAULT b'1' COMMENT '是否展开'")
    private boolean expanded;

    @Column(columnDefinition = "BIT NOT NULL DEFAULT b'1' COMMENT '是否叶子节点'")
    private boolean leaf;

    @Column(columnDefinition = "VARCHAR(128) NOT NULL DEFAULT '' COMMENT '树形结构序列,记录所有上级节点,格式:1.2.3'")
    private String treeSeq = StringUtils.EMPTY;

    @Transient
    private List<? extends TreeEntity> children = Collections.emptyList();

    /**
     * 获取id
     *
     * @return id
     */
    public long getId() {
        return super.getId();
    }

    /**
     * 获取parentId
     *
     * @return parentId
     */
    public Long getParentId() {
        return parentId;
    }


}
