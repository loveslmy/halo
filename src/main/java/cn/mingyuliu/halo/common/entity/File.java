package cn.mingyuliu.halo.common.entity;

import cn.mingyuliu.halo.common.entity.base.BaseEntity;
import cn.mingyuliu.halo.common.enums.FileType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


/**
 * <pre>
 *     文件实体
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/11/09
 */
@Getter
@Setter
@Entity
@Table(name = "file", indexes = {
        @Index(name = "name_index", columnList = "name"),
        @Index(name = "create_date_index", columnList = "crtDate"),
        @Index(name = "update_date_index", columnList = "updDate")})
public class File extends BaseEntity {

    @Column(columnDefinition = "VARCHAR(64) NOT NULL DEFAULT '' COMMENT '文件名称'")
    private String name;

    @Column(columnDefinition = "BIGINT NOT NULL default 0 COMMENT '文件大小:bytes'")
    private long size;

    @Column(columnDefinition = "VARCHAR(8) NOT NULL DEFAULT '' COMMENT '文件类型:文件扩展名'")
    private String suffix;

    @Column(columnDefinition = "TINYINT NOT NULL DEFAULT 0 COMMENT '文件类型'")
    private FileType fileType;

    @Column(columnDefinition = "CHAR(32) NOT NULL DEFAULT '' COMMENT 'MD5'", unique = true)
    private String md5;

    @Column(columnDefinition = "BIGINT NOT NULL DEFAULT 0 COMMENT '内容id'")
    private long refId;

    /**
     * 文件二进制内容
     */
    @Transient
    private byte[] datas;

    /**
     * 文件文本内容
     */
    @Transient
    private String content;

}
