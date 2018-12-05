package cn.mingyuliu.halo.common.entity;

import cn.mingyuliu.halo.common.entity.base.BaseEntity;
import cn.mingyuliu.halo.common.enums.UserType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * <pre>
 *     用户信息表
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/08/31
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    private static final long serialVersionUID = -5144055068797033748L;

    @NotBlank(message = "用户名不能为空")
    @Column(columnDefinition = "VARCHAR(16) NOT NULL COMMENT '用户名'",
            updatable = false, unique = true)
    private String userName;

    @NotBlank(message = "昵称不能为空")
    @Column(columnDefinition = "VARCHAR(16) NOT NULL COMMENT '昵称'",
            unique = true)
    private String userDisplayName;

    @NotBlank(message = "密码不能为空")
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '密码'")
    private String userPass;

    @Email(message = "邮箱格式不正确")
    @Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '邮箱'",
            unique = true)
    private String userEmail;

    @Column(columnDefinition = "VARCHAR(64) COMMENT '头像'")
    private String userAvatar;

    @Column(columnDefinition = "VARCHAR(32) COMMENT '描述'")
    private String userDesc;

    @Column(columnDefinition = "TINYINT DEFAULT 0 COMMENT '用户类型'")
    private UserType userType;

    @Type(type = "yes_no")
    @Column(columnDefinition = "BIT NOT NULL DEFAULT b'1' COMMENT '是否禁用登录'")
    private boolean loginEnable = true;

    @Column(columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录时间'")
    private Date loginLast;

    @Column(columnDefinition = "TINYINT(4) DEFAULT 0 COMMENT '登录错误次数记录'")
    private byte loginError = 0;

}
