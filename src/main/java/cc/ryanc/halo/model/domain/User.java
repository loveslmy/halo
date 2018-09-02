package cc.ryanc.halo.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 *     博主信息
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2017/11/14
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "halo_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = -5144055068797033748L;

    @NotBlank(message = "用户名不能为空")
    @JsonIgnore
    @Column(length = 16, columnDefinition = "CHAR(16) NOT NULL COMMENT '用户名'",
            updatable = false, unique = true)
    private String userName;

    @NotBlank(message = "昵称不能为空")
    @Column(length = 16, columnDefinition = "CHAR(16) NOT NULL COMMENT '昵称'",
            unique = true)
    private String userDisplayName;

    @NotBlank(message = "密码不能为空")
    @JsonIgnore
    @Column(length = 32, columnDefinition = "CHAR(32) NOT NULL COMMENT '密码'",
            unique = true)
    private String userPass;

    @Email(message = "邮箱格式不正确")
    @Column(length = 64, columnDefinition = "VARCHAR(64) NOT NULL COMMENT '邮箱'",
            unique = true)
    private String userEmail;

    @Column(length = 64, columnDefinition = "VARCHAR(64) COMMENT '头像'")
    private String userAvatar;

    @Column(length = 32, columnDefinition = "VARCHAR(32) COMMENT '描述'")
    private String userDesc;

    @JsonIgnore
    @Type(type = "yes_no")
    @Column(columnDefinition = "BIT NOT NULL DEFAULT b'1' COMMENT '是否禁用登录'")
    private boolean loginEnable = true;

    @JsonIgnore
    @Column(columnDefinition = "TIMESTAMP COMMENT '最后登录时间'")
    private Date loginLast;

    @JsonIgnore
    @Column(columnDefinition = "TINYINT(4) NOT NULL DEFAULT '0' COMMENT '登录错误次数记录'")
    private byte loginError = 0;

}
