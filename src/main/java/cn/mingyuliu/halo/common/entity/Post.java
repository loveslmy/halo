package cn.mingyuliu.halo.common.entity;

import cn.mingyuliu.halo.common.entity.base.BaseEntity;
import cn.mingyuliu.halo.common.enums.PostStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * <pre>
 *     文章
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/09/02
 */
@Getter
@Setter
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    private static final long serialVersionUID = -6019684584665869629L;

    @Column(length = 48, columnDefinition = "VARCHAR(48) NOT NULL COMMENT '文章标题'", unique = true)
    private String name;

    @Column(length = 128, columnDefinition = "VARCHAR(128) NOT NULL COMMENT '文章摘要'")
    private String description;

    @Column(columnDefinition = "BIGINT COMMENT '文章缩略图'")
    private Long refId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发表日期'")
    private Date pubDate;

    @Column(columnDefinition = "TINYINT DEFAULT 0 COMMENT '文章状态'")
    private PostStatus postStatus = PostStatus.DRAFT;

    @Column(columnDefinition = "BIGINT DEFAULT 0 COMMENT '文章访问量'")
    private long visitCount = 0L;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章所属分类
     */
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, targetEntity = Category.class)
    @JoinTable(name = "posts_categories",
            joinColumns = {@JoinColumn(name = "post_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "cate_id", nullable = false)})
    private Set<Category> categories;

    /**
     * 文章所属标签
     */
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, targetEntity = Tag.class)
    @JoinTable(name = "posts_tags",
            joinColumns = {@JoinColumn(name = "post_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", nullable = false)})
    private Set<Tag> tags;

}
