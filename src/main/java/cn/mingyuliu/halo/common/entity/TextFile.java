package cn.mingyuliu.halo.common.entity;

import cn.mingyuliu.halo.common.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * <pre>
 *     文本文件
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/11/09
 */
@Getter
@Setter
@Entity
@Table(name = "text_file")
public class TextFile extends BaseEntity {

    @Column(columnDefinition = "MEDIUMTEXT NOT NULL COMMENT '文本内容'")
    @Lob
    private String content;

}
