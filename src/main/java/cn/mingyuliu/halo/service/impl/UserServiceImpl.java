package cn.mingyuliu.halo.service.impl;

import cn.mingyuliu.halo.model.domain.User;
import cn.mingyuliu.halo.model.dto.InstallDto;
import cn.mingyuliu.halo.model.enums.UserType;
import cn.mingyuliu.halo.repository.UserRepository;
import cn.mingyuliu.halo.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *     用户服务实现类
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/03
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserRepository userRepository;

    /**
     * (non-Javadoc)
     *
     * @see IUserService#createOwnerUser(InstallDto)
     */
    @Override
    public void createOwnerUser(InstallDto installDto) {
        String userName = installDto.getUserName();
        User user = userRepository.findByUserNameAndUserType(userName, UserType.OWNER);

        if (user == null) {
            user = userRepository.findByUserType(UserType.OWNER);
        }

        if (user == null) {
            user = new User();
        }

        user.setUserName(installDto.getUserName());
        user.setUserDisplayName(installDto.getUserDisplayName());
        user.setUserEmail(installDto.getUserEmail());
        user.setUserPass(DigestUtils.md5DigestAsHex(installDto.getUserPwd().getBytes()));
        user.setUserType(UserType.OWNER);
        userRepository.save(user);
    }

    /**
     * (non-Javadoc)
     *
     * @see IUserService#saveByUser(User)
     */
    @Override
    public void saveByUser(User user) {
        userRepository.save(user);
    }

    /**
     * (non-Javadoc)
     *
     * @see IUserService#userLoginByName(String userName, String userPass)
     */
    @Override
    public User userLoginByName(String userName, String userPass) {
        return userRepository.findByUserNameAndUserPass(userName, userPass);
    }

    /**
     * (non-Javadoc)
     *
     * @see IUserService#userLoginByEmail(String userEmail, String userPass)
     */
    @Override
    public User userLoginByEmail(String userEmail, String userPass) {
        return userRepository.findByUserEmailAndUserPass(userEmail, userPass);
    }

    /**
     * (non-Javadoc)
     *
     * @see IUserService#findOwnerUser()
     */
    @Override
    public User findOwnerUser() {
        return userRepository.findByUserType(UserType.OWNER);
    }

    /**
     * 验证修改密码时，密码是否正确
     *
     * @param userId   userId
     * @param userPass userPass
     * @return User
     */
    @Override
    public User findByUserIdAndUserPass(Long userId, String userPass) {
        return userRepository.findByIdAndUserPass(userId, userPass);
    }

    /**
     * 修改禁用状态
     *
     * @param enable enable
     */
    @Override
    public void updateUserLoginEnable(boolean enable) {
        User user = this.findOwnerUser();
        user.setLoginEnable(enable);
        userRepository.save(user);
    }

    /**
     * 修改最后登录时间
     *
     * @param lastDate 最后登录时间
     * @return User
     */
    @Override
    public User updateUserLoginLast(Date lastDate) {
        User user = this.findOwnerUser();
        user.setLoginLast(lastDate);
        userRepository.save(user);
        return user;
    }

    /**
     * 增加登录错误次数
     *
     * @return 登录错误次数
     */
    @Override
    public byte updateUserLoginError() {
        User user = this.findOwnerUser();
        int loginError = user.getLoginError();
        if (loginError != 0) {
            user.setLoginError((byte) ++loginError);
        }
        userRepository.save(user);
        return user.getLoginError();
    }

    /**
     * 修改用户的状态为正常
     *
     * @return User
     */
    @Override
    public User updateUserNormal() {
        User user = this.findOwnerUser();
        user.setLoginEnable(true);
        user.setLoginError((byte) 0);
        user.setLoginLast(new Date());
        userRepository.save(user);
        return user;
    }

}
