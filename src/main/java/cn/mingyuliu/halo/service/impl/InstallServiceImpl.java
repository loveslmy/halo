package cn.mingyuliu.halo.service.impl;

import cn.mingyuliu.halo.config.sys.OptionHolder;
import cn.mingyuliu.halo.exception.RepetInstallException;
import cn.mingyuliu.halo.model.domain.Menu;
import cn.mingyuliu.halo.model.dto.InstallDto;
import cn.mingyuliu.halo.model.enums.Option;
import cn.mingyuliu.halo.service.*;
import freemarker.template.Configuration;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;

/**
 * <pre>
 *     安装服务接口实现
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/06
 */
@Service
public class InstallServiceImpl implements IInstallService {

    @Resource
    private OptionHolder optionHolder;

    @Resource
    private IUserService userService;

    @Resource
    private LogsService logsService;

    @Resource
    private PostService postService;

    @Resource
    private ICategoryService categoryService;

    @Resource
    private CommentService commentService;

    @Resource
    private MenuService menuService;

    @Resource
    private Configuration configuration;


    /**
     * (non-Javadoc)
     *
     * @see IInstallService#install(InstallDto, HttpServletRequest)
     */
    @Override
    @Transactional
    public void install(InstallDto installDto, HttpServletRequest request) {
        if (optionHolder.getBoolean(Option.IS_INSTALL)) {
            throw new RepetInstallException();
        }
        // 1 create owner user
        userService.createOwnerUser(installDto);
        // 2 create default category
        categoryService.createDefaultCategory();
        // 3 init options
        initOptions(installDto);


        Menu menuIndex = new Menu();
        menuIndex.setMenuName("最近更新");
        menuIndex.setMenuUrl("/");
        menuIndex.setMenuSort(1);
        menuIndex.setMenuIcon("");
        menuService.saveByMenu(menuIndex);

        Menu menuArchive = new Menu();
        menuArchive.setMenuName("全部文章");
        menuArchive.setMenuUrl("/archives");
        menuArchive.setMenuSort(2);
        menuArchive.setMenuIcon("");
        menuService.saveByMenu(menuArchive);

        // 4 insert log
        /*logsService.saveByLogs(new OpLog(LogsRecord.INSTALL, "安装成功。", HaloUtils.getClientIP(request), new Date()));
            configuration.setSharedVariable("options", IOptionService.loadAllOptions());
            configuration.setSharedVariable("user", userService.findOwnerUser());*/
    }

    private void initOptions(InstallDto installDto) {
        // 保存博客标题和博客地址设置
        optionHolder.set(Option.BLOG_TITLE, installDto.getBlogTitle());
        optionHolder.set(Option.BLOG_URL, installDto.getBlogUrl());
        // 设置默认主题
        optionHolder.set(Option.THEME, "anatole");
        // 安装成功标识
        optionHolder.set(Option.IS_INSTALL, Boolean.TRUE.toString());
        // 博客建立时间
        optionHolder.set(Option.BLOG_START, DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        String falseStr = Boolean.FALSE.toString();
        // 默认不配置邮件系统
        optionHolder.set(Option.SMTP_EMAIL_ENABLE, falseStr);

        // 新评论，审核通过，回复，默认不通知
        optionHolder.set(Option.NEW_COMMENT_NOTICE, falseStr);
        optionHolder.set(Option.COMMENT_PASS_NOTICE, falseStr);
        optionHolder.set(Option.COMMENT_REPLY_NOTICE, falseStr);
        // 重新加载选项
        optionHolder.loadOptions();
    }

}
