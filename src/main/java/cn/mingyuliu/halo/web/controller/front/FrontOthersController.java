package cn.mingyuliu.halo.web.controller.front;

import cn.mingyuliu.halo.config.sys.OptionHolder;
import cn.mingyuliu.halo.model.domain.Post;
import cn.mingyuliu.halo.model.enums.Option;
import cn.mingyuliu.halo.model.enums.PostStatus;
import cn.mingyuliu.halo.service.PostService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 *     sitemap，rss页面控制器
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/4/26
 */
@Controller
public class FrontOthersController {

    @Resource
    private OptionHolder optionHolder;

    @Resource
    private PostService postService;

    /**
     * 获取文章rss
     *
     * @return rss
     */
    @GetMapping(value = {"feed", "feed.xml", "atom", "atom.xml"}, produces = "application/xml;charset=UTF-8")
    @ResponseBody
    public String feed() {
        String rssPosts = optionHolder.get(Option.RSS_POSTS);
        if (StringUtils.isBlank(rssPosts)) {
            rssPosts = "20";
        }
        //获取文章列表并根据时间排序
        Sort sort = new Sort(Sort.Direction.DESC, "postDate");
        Pageable pageable = PageRequest.of(0, Integer.parseInt(rssPosts), sort);
        Page<Post> postsPage = postService.findPostByStatus(PostStatus.PUBLISHED, pageable);
        List<Post> posts = postsPage.getContent();
        return postService.buildRss(posts);
    }

    /**
     * 获取sitemap
     *
     * @return sitemap
     */
    @GetMapping(value = {"sitemap", "sitemap.xml"}, produces = "application/xml;charset=UTF-8")
    @ResponseBody
    public String siteMap() {
        //获取文章列表并根据时间排序
        Sort sort = new Sort(Sort.Direction.DESC, "postDate");
        Pageable pageable = PageRequest.of(0, 999, sort);
        Page<Post> postsPage = postService.findPostByStatus(PostStatus.PUBLISHED, pageable);
        List<Post> posts = postsPage.getContent();
        return postService.buildSiteMap(posts);
    }
}
