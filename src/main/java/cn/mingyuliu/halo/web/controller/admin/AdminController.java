package cn.mingyuliu.halo.web.controller.admin;

import cn.mingyuliu.halo.model.domain.Comment;
import cn.mingyuliu.halo.model.domain.OpLog;
import cn.mingyuliu.halo.model.domain.Post;
import cn.mingyuliu.halo.model.domain.User;
import cn.mingyuliu.halo.model.dto.HaloConst;
import cn.mingyuliu.halo.model.dto.JsonResult;
import cn.mingyuliu.halo.model.dto.LogsRecord;
import cn.mingyuliu.halo.model.enums.ResponseStatus;
import cn.mingyuliu.halo.service.*;
import cn.mingyuliu.halo.utils.HaloUtils;
import cn.mingyuliu.halo.web.controller.core.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *     后台首页控制器
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2017/12/5
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends BaseController {

    @Resource
    private PostService postService;

    @Resource
    private IUserService userService;

    @Resource
    private LogsService logsService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private CommentService commentService;

    @Resource
    private AttachmentService attachmentService;

    /**
     * 请求后台页面
     *
     * @param model model
     * @return 模板路径admin/admin_index
     */
    @GetMapping(value = {"", "/index"})
    public String index(Model model) {

        //查询评论的条数
        int commentCount = commentService.findAllComments().size();
        model.addAttribute("commentCount", commentCount);

        //查询最新的文章
        List<Post> postsLatest = postService.findPostLatest();
        model.addAttribute("postTopFive", postsLatest);

        //查询最新的日志
        List<OpLog> opLogLatest = logsService.findLogsLatest();
        model.addAttribute("logs", opLogLatest);

        //查询最新的评论
        List<Comment> comments = commentService.findCommentsLatest();
        model.addAttribute("comments", comments);

        //附件数量
        model.addAttribute("mediaCount", attachmentService.findAllAttachments().size());

        //文章阅读总数
        long postViewsSum = postService.getPostViews();
        model.addAttribute("postViewsSum", postViewsSum);
        return "admin/admin_index";
    }

    /**
     * 处理跳转到登录页的请求
     *
     * @param session session
     * @return 模板路径admin/admin_login
     */
    @GetMapping(value = "/login")
    public String login(HttpSession session) {
        User user = (User) session.getAttribute(HaloConst.USER_SESSION_KEY);
        //如果session存在，跳转到后台首页
        if (null != user) {
            return "redirect:/admin";
        }
        return "admin/admin_login";
    }

    /**
     * 验证登录信息
     *
     * @param loginName 登录名：邮箱／用户名
     * @param loginPwd  loginPwd 密码
     * @param session   session session
     * @return JsonResult JsonResult
     */
    @PostMapping(value = "/getLogin")
    @ResponseBody
    public JsonResult getLogin(@ModelAttribute("loginName") String loginName,
                               @ModelAttribute("loginPwd") String loginPwd,
                               HttpSession session) {
        // 已注册账号，单用户，只有一个
        User user = userService.findOwnerUser();

        if (!user.isLoginEnable()) {
            //首先判断是否已经被禁用已经是否已经过了10分钟
            Date loginLast = user.getLoginLast();
            if (null == loginLast) {
                loginLast = new Date();
            }
            DateUtils.addMinutes(loginLast, 10);
            if (loginLast.after(new Date())) {
                return new JsonResult<>(ResponseStatus.FAIL, "已禁止登录，请10分钟后再试");
            }
        }
        userService.updateUserLoginLast(new Date());

        // 验证用户名和密码
        if (!DigestUtils.md5DigestAsHex(loginPwd.getBytes()).equals(user.getUserPass())) {
            //更新失败次数
            int errorCount = userService.updateUserLoginError();
            //超过五次禁用账户
            if (errorCount >= 5) {
                userService.updateUserLoginEnable(Boolean.FALSE);
            }
            logsService.saveByLogs(
                    new OpLog(LogsRecord.LOGIN, LogsRecord.LOGIN_ERROR + "["
                            + StringEscapeUtils.escapeHtml4(loginName)
                            + "," + StringEscapeUtils.escapeHtml4(loginPwd) + "]",
                            HaloUtils.getClientIP(request),
                            new Date()
                    )
            );
            return new JsonResult<>(ResponseStatus.FAIL, "登录失败，您还有" + (5 - errorCount) + "次重试机会。");
        }

        session.setAttribute(HaloConst.USER_SESSION_KEY, user);
        // 重置用户的登录状态为正常
        userService.updateUserNormal();
        logsService.saveByLogs(new OpLog(LogsRecord.LOGIN, LogsRecord.LOGIN_SUCCESS,
                HaloUtils.getClientIP(request), new Date()));
        log.info("用户[{}]登录成功。", user.getUserDisplayName());
        return new JsonResult<>(ResponseStatus.FAIL, "登录成功！");
    }

    /**
     * 退出登录 销毁session
     *
     * @param session session
     * @return 重定向到/admin/login
     */
    @GetMapping(value = "/logOut")
    public String logOut(HttpSession session) {
        User user = (User) session.getAttribute(HaloConst.USER_SESSION_KEY);
        logsService.saveByLogs(new OpLog(LogsRecord.LOGOUT, user.getUserName(),
                HaloUtils.getClientIP(request), new Date()));
        session.invalidate();
        log.info("用户[{}]退出登录", user.getUserName());
        return "redirect:/admin/login";
    }

    /**
     * 查看所有日志
     *
     * @param model model model
     * @param page  page 当前页码
     * @param size  size 每页条数
     * @return 模板路径admin/widget/_logs-all
     */
    @GetMapping(value = "/logs")
    public String logs(Model model,
                       @RequestParam(value = "page", defaultValue = "0") Integer page,
                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "logId");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<OpLog> logs = logsService.findAllLogs(pageable);
        model.addAttribute("logs", logs);
        return "admin/widget/_logs-all";
    }

    /**
     * 清除所有日志
     *
     * @return 重定向到/admin
     */
    @GetMapping(value = "/logs/clear")
    public String logsClear() {
        try {
            logsService.removeAllLogs();
        } catch (Exception e) {
            log.error("清除日志失败：{}" + e.getMessage());
        }
        return "redirect:/admin";
    }

    /**
     * 不可描述的页面
     *
     * @return 模板路径admin/admin_halo
     */
    @GetMapping(value = "/halo")
    public String halo() {
        return "admin/admin_halo";
    }

}
