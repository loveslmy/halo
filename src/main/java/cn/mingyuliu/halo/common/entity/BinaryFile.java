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
 *     二进制文件
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/11/09
 */
@Getter
@Setter
@Entity
@Table(name = "binary_file")
public class BinaryFile extends BaseEntity {

    @Column(columnDefinition = "MEDIUMBLOB NOT NULL COMMENT '二进制数据'")
    @Lob
    private byte[] datas;

}
