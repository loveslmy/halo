package cn.mingyuliu.halo.common.entity;

import cn.mingyuliu.halo.common.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *     评论
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/09/02
 */
@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

    private static final long serialVersionUID = -6639021627094260505L;

    /**
     * 评论文章
     */
    @ManyToOne(targetEntity = Post.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    /**
     * 评论人
     */
    @NotBlank(message = "评论用户名不能为空")
    private String commentAuthor;

    /**
     * 评论人的邮箱
     */
    @Email(message = "邮箱格式不正确")
    private String commentAuthorEmail;

    /**
     * 评论人的主页
     */
    private String commentAuthorUrl;

    /**
     * 评论人的ip
     */
    @JsonIgnore
    private String commentAuthorIp;

    /**
     * 评论人的头像，用于avatar
     */
    private String commentAuthorAvatarMd5;

    /**
     * 评论时间
     */
    private Date commentDate;

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    @Lob
    private String commentContent;

    /**
     * 评论者ua信息
     */
    private String commentAgent;

    /**
     * 上一级
     */
    private Long commentParent = 0L;

    /**
     * 评论状态，0：正常，1：待审核，2：回收站
     */
    private Integer commentStatus = 1;

    /**
     * 是否是博主的评论 0:不是 1:是
     */
    private Integer isAdmin;

    /**
     * 当前评论下的所有子评论
     */
    @Transient
    private List<Comment> childComments;
}
