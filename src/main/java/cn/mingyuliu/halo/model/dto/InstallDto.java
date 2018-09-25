package cn.mingyuliu.halo.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 安装信息Model
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/02
 */
@Data
public class InstallDto {

    @NotEmpty(message = "blog.title.not.empty")
    private String blogTitle;
    @NotEmpty(message = "blog.url.not.empty")
    private String blogUrl;
    @NotEmpty(message = "user.name.not.empty")
    private String userName;
    @NotEmpty(message = "user.display.name.not.empty")
    private String userDisplayName;
    @NotEmpty(message = "user.email.not.empty")
    private String userEmail;
    @NotEmpty(message = "user.password.not.empty")
    private String userPwd;

}
