package cn.mingyuliu.halo.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * <pre>
 *     文章内容
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/02
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "post_content")
public class PostContent extends BaseEntity {

    @Lob
    @Column(unique = true, columnDefinition = "MEDIUMTEXT COMMENT '文章内容'")
    private String content;


}
