package cn.mingyuliu.halo.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <pre>
 *     附件
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/02
 */
@Getter
@Setter
@Entity
@Table(name = "halo_attachment")
public class Attachment extends BaseEntity {

    private static final long serialVersionUID = 3060117944880138064L;

    @Column(length = 48, columnDefinition = "VARCHAR(48) NOT NULL COMMENT '附件名称'")
    private String attachName;

    @Column(columnDefinition = "MEDIUMBLOB NOT NULL COMMENT '附件数据'")
    private byte[] data;

    @Column(length = 8, columnDefinition = "VARCHAR(8) DEFAULT '' COMMENT '附件类型'")
    private String attachType;

    @Column(length = 8, columnDefinition = "VARCHAR(8) NOT NULL COMMENT '附件后缀'")
    private String attachSuffix;

    @Column(columnDefinition = "INT UNSIGNED NOT NULL COMMENT '附件大小'")
    private long attachSize;

}
