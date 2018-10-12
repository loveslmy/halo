package cn.mingyuliu.halo.common.repository;

import cn.mingyuliu.halo.common.entity.User;
import cn.mingyuliu.halo.common.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 *     用户持久层
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/03
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户类型查询
     *
     * @param userType {@link UserType}
     * @return {@link User}
     */
    User findByUserType(UserType userType);

    /**
     * 根据用户名和用户类型查询
     *
     * @param userName 用户登录名
     * @param userType {@link UserType}
     * @return {@link User}
     */
    User findByUserNameAndUserType(String userName, UserType userType);

    /**
     * 根据用户名和密码查询
     *
     * @param userName 用户登录名
     * @param userPass 用户密码
     * @return {@link User}
     */
    User findByUserNameAndUserPass(String userName, String userPass);

    /**
     * 根据邮箱和密码查询
     *
     * @param userEmail 用户email
     * @param userPass  用户密码
     * @return {@link User}
     */
    User findByUserEmailAndUserPass(String userEmail, String userPass);

    /**
     * 根据用户编号和密码查询
     *
     * @param userId   用户id
     * @param userPass 用户密码
     * @return {@link User}
     */
    User findByIdAndUserPass(Long userId, String userPass);

}
