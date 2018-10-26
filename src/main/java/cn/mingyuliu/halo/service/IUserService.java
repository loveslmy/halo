package cn.mingyuliu.halo.service;

import cn.mingyuliu.halo.common.dto.InstallDto;
import cn.mingyuliu.halo.common.entity.User;

import java.util.Date;

/**
 * <pre>
 *     用户服务接口
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/09/03
 */
public interface IUserService {

    /**
     * 创建Owner用户
     *
     * @param installDto {@link InstallDto}
     */
    void createOwnerUser(InstallDto installDto);

    /**
     * 保存个人资料
     *
     * @param user user
     */
    void saveByUser(User user);

    /**
     * 根据用户名和密码查询，用于登录
     *
     * @param userName userName
     * @param userPass userPass
     * @return User
     */
    User userLoginByName(String userName, String userPass);

    /**
     * 根据邮箱和密码查询，用户登录
     *
     * @param userEmail userEmail
     * @param userPass  userPass
     * @return User
     */
    User userLoginByEmail(String userEmail, String userPass);

    /**
     * 查询所有用户
     *
     * @return User
     */
    User findOwnerUser();

    /**
     * 根据用户编号和密码查询
     *
     * @param userId   userid
     * @param userPass userpass
     * @return User
     */
    User findByUserIdAndUserPass(Long userId, String userPass);

    /**
     * 修改禁用状态
     *
     * @param enable enable
     */
    void updateUserLoginEnable(boolean enable);

    /**
     * 修改最后登录时间
     *
     * @param lastDate 最后登录时间
     * @return User
     */
    User updateUserLoginLast(Date lastDate);

    /**
     * 增加登录错误次数
     *
     * @return 登录错误次数
     */
    byte updateUserLoginError();

    /**
     * 修改用户的状态为正常
     *
     * @return User
     */
    User updateUserNormal();
}
