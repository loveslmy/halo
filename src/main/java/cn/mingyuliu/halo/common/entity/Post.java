package cn.mingyuliu.halo.common.entity;

import cn.mingyuliu.halo.common.entity.base.BaseEntity;
import cn.mingyuliu.halo.common.enums.Allow;
import cn.mingyuliu.halo.common.enums.PostStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    private String postTitle;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL COMMENT '文章路径'", unique = true)
    private String postUrl;

    @Column(length = 128, columnDefinition = "VARCHAR(128) NOT NULL COMMENT '文章摘要'")
    private String postSummary;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '缩略图'")
    private String postThumbnail;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(columnDefinition = "TIMESTAMP COMMENT '发表日期'")
    private Date postDate;

    @Column(columnDefinition = "TINYINT DEFAULT 0 COMMENT '文章状态'")
    private PostStatus postStatus = PostStatus.DRAFT;

    @Column(columnDefinition = "TINYINT DEFAULT 0 COMMENT '是否允许评论'")
    private Allow allowComment = Allow.DISALLOW;

    @Column(columnDefinition = "BIGINT DEFAULT 0 COMMENT '文章访问量'")
    private long visitCount = 0L;

    /**
     * 文章内容
     */
    @OneToOne(fetch = FetchType.LAZY, targetEntity = PostContent.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "content_id", referencedColumnName = "id")
    private PostContent postContent;

    /**
     * 文章所属分类
     */
    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY, targetEntity = Category.class)
    @JoinTable(name = "posts_categories",
            joinColumns = {@JoinColumn(name = "post_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "cate_id", nullable = false)})
    private List<Category> categories;

    /**
     * 文章所属标签
     */
    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY, targetEntity = Tag.class)
    @JoinTable(name = "posts_tags",
            joinColumns = {@JoinColumn(name = "post_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", nullable = false)})
    private List<Tag> tags;

    /**
     * 文章的评论
     */
    @OneToMany(mappedBy = "post", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, targetEntity = Comment.class)
    private List<Comment> comments;

}
