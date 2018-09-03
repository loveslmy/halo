package cn.mingyuliu.halo.service.impl;

import cn.mingyuliu.halo.config.OptionHolder;
import cn.mingyuliu.halo.model.domain.Category;
import cn.mingyuliu.halo.model.domain.Post;
import cn.mingyuliu.halo.model.domain.Tag;
import cn.mingyuliu.halo.model.dto.Archive;
import cn.mingyuliu.halo.model.dto.HaloConst;
import cn.mingyuliu.halo.model.enums.OptionEnum;
import cn.mingyuliu.halo.model.enums.PostStatusEnum;
import cn.mingyuliu.halo.repository.PostRepository;
import cn.mingyuliu.halo.service.PostService;
import cn.mingyuliu.halo.utils.HaloUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <pre>
 *     文章业务逻辑实现类
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2017/11/14
 */
@Service
public class PostServiceImpl implements PostService {

    private static final String POSTS_CACHE_NAME = "posts";

    private static final String COMMENTS_CACHE_NAME = "comments";

    @Resource
    private OptionHolder optionHolder;

    @Resource
    private PostRepository postRepository;

    /**
     * 保存文章
     *
     * @param post Post
     * @return Post
     */
    @Override
    @CacheEvict(value = {POSTS_CACHE_NAME, COMMENTS_CACHE_NAME}, allEntries = true, beforeInvocation = true)
    public Post saveByPost(Post post) {
        return postRepository.save(post);
    }

    /**
     * 根据编号移除文章
     *
     * @param postId postId
     * @return Post
     */
    @Override
    @CacheEvict(value = {POSTS_CACHE_NAME, COMMENTS_CACHE_NAME}, allEntries = true, beforeInvocation = true)
    public Post removeByPostId(long postId) {
        Optional<Post> postOpt = this.findByPostId(postId);
        if (!postOpt.isPresent()) {
            return null;
        }
        Post post = postOpt.get();
        postRepository.delete(post);
        return post;
    }

    /**
     * 修改文章状态
     *
     * @param postId postId
     * @param status status
     * @return Post
     */
    @Override
    @CacheEvict(value = POSTS_CACHE_NAME, allEntries = true, beforeInvocation = true)
    public Post updatePostStatus(long postId, PostStatusEnum status) {
        Optional<Post> post = this.findByPostId(postId);
        post.get().setPostStatus(status);
        return postRepository.save(post.get());
    }

    /**
     * 修改文章阅读量
     *
     * @param post post
     */
    @Override
    public void updatePostView(Post post) {
        post.setVisitCount(post.getVisitCount() + 1);
        postRepository.save(post);
    }

    /**
     * 批量更新文章摘要
     *
     * @param postSummary postSummary
     */
    @Override
    @CacheEvict(value = POSTS_CACHE_NAME, allEntries = true, beforeInvocation = true)
    public void updateAllSummary(int postSummary) {
/*        List<Post> posts = this.findAllPosts();
        for (Post post : posts) {
            String text = HaloUtils.cleanHtmlTag(post.getPostContent());
            if (text.length() > postSummary) {
                post.setPostSummary(text.substring(0, postSummary));
            } else {
                post.setPostSummary(text);
            }
            postRepository.save(post);
        }*/
    }

    /**
     * 获取文章列表 分页
     *
     * @param pageable 分页信息
     * @return Page
     */
    @Override
    public Page<Post> findAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    /**
     * 获取文章列表 不分页
     *
     * @return List
     */
    @Override
    @Cacheable(value = POSTS_CACHE_NAME, key = "'posts_type_'+#postType")
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    /**
     * 模糊查询文章
     *
     * @param keyWord  keyword
     * @param pageable pageable
     * @return List
     */
    @Override
    public List<Post> searchPosts(String keyWord, Pageable pageable) {
        return postRepository.findByPostTitleLike(keyWord, pageable);
    }

    /**
     * 根据文章状态查询 分页，用于后台管理
     *
     * @param status   0，1，2
     * @param pageable 分页信息
     * @return Page
     */
    @Override
    public Page<Post> findPostByStatus(PostStatusEnum status, Pageable pageable) {
        return postRepository.findPostsByPostStatus(status, pageable);
    }

    /**
     * 根据文章状态查询 分页，首页分页
     *
     * @param pageable pageable
     * @return Page
     */
    @Override
    @Cacheable(value = POSTS_CACHE_NAME, key = "'posts_page_'+#pageable.pageNumber")
    public Page<Post> findPostByStatus(Pageable pageable) {
        return postRepository.findPostsByPostStatus(PostStatusEnum.PUBLISHED, pageable);
    }

    /**
     * 根据文章状态查询
     *
     * @param status 0，1，2
     * @return List
     */
    @Override
    @Cacheable(value = POSTS_CACHE_NAME, key = "'posts_status_type_'+#status")
    public List<Post> findPostByStatus(PostStatusEnum status) {
        return postRepository.findPostsByPostStatus(status);
    }

    /**
     * 根据编号查询文章
     *
     * @param postId postId
     * @return Optional
     */
    @Override
    public Optional<Post> findByPostId(long postId) {
        return postRepository.findById(postId);
    }

    /**
     * 根据文章路径查询
     *
     * @param postUrl 路径
     * @return Post
     */
    @Override
    @Cacheable(value = POSTS_CACHE_NAME, key = "'posts_posturl_'+#postUrl")
    public Post findByPostUrl(String postUrl) {
        return postRepository.findPostByPostUrl(postUrl);
    }

    /**
     * 查询最新的5篇文章
     *
     * @return List
     */
    @Override
    @Cacheable(value = POSTS_CACHE_NAME, key = "'posts_latest'")
    public List<Post> findPostLatest() {
        return postRepository.findTopFive();
    }

    /**
     * 查询之后的文章
     *
     * @param postDate 发布时间
     * @return List
     */
    @Override
    public List<Post> findByPostDateAfter(Date postDate) {
        return postRepository.findByPostDateAfterAndPostStatusOrderByPostDateDesc(postDate, PostStatusEnum.PUBLISHED);
    }

    /**
     * 查询Id之前的文章
     *
     * @param postDate 发布时间
     * @return List
     */
    @Override
    public List<Post> findByPostDateBefore(Date postDate) {
        return postRepository.findByPostDateBeforeAndPostStatusOrderByPostDateAsc(postDate, PostStatusEnum.PUBLISHED);
    }


    /**
     * 查询归档信息 根据年份和月份
     *
     * @return List
     */
    @Override
    @Cacheable(value = POSTS_CACHE_NAME, key = "'archives_year_month'")
    public List<Archive> findPostGroupByYearAndMonth() {
        List<Object[]> objects = postRepository.findPostGroupByYearAndMonth();
        List<Archive> archives = new ArrayList<>();
        Archive archive;
        for (Object[] obj : objects) {
            archive = new Archive();
            archive.setYear(obj[0].toString());
            archive.setMonth(obj[1].toString());
            archive.setCount(obj[2].toString());
            archive.setPosts(this.findPostByYearAndMonth(obj[0].toString(), obj[1].toString()));
            archives.add(archive);
        }
        return archives;
    }

    /**
     * 查询归档信息 根据年份
     *
     * @return List
     */
    @Override
    @Cacheable(value = POSTS_CACHE_NAME, key = "'archives_year'")
    public List<Archive> findPostGroupByYear() {
        List<Object[]> objects = postRepository.findPostGroupByYear();
        List<Archive> archives = new ArrayList<>();
        Archive archive;
        for (Object[] obj : objects) {
            archive = new Archive();
            archive.setYear(obj[0].toString());
            archive.setCount(obj[1].toString());
            archive.setPosts(this.findPostByYear(obj[0].toString()));
            archives.add(archive);
        }
        return archives;
    }

    /**
     * 根据年份和月份查询文章
     *
     * @param year  year
     * @param month month
     * @return List
     */
    @Override
    @Cacheable(value = POSTS_CACHE_NAME, key = "'posts_year_month_'+#year+'_'+#month")
    public List<Post> findPostByYearAndMonth(String year, String month) {
        return postRepository.findPostByYearAndMonth(year, month);
    }

    /**
     * 根据年份查询文章
     *
     * @param year year
     * @return List
     */
    @Override
    @Cacheable(value = POSTS_CACHE_NAME, key = "'posts_year_'+#year")
    public List<Post> findPostByYear(String year) {
        return postRepository.findPostByYear(year);
    }

    /**
     * 根据年份和月份索引文章
     *
     * @param year     year year
     * @param month    month month
     * @param pageable pageable pageable
     * @return Page
     */
    @Override
    public Page<Post> findPostByYearAndMonth(String year, String month, Pageable pageable) {
        return postRepository.findPostByYearAndMonth(year, month, null);
    }

    /**
     * 根据分类目录查询文章
     *
     * @param category category
     * @param pageable pageable
     * @return Page
     */
    @Override
    @CachePut(value = POSTS_CACHE_NAME, key = "'posts_category_'+#category.cateId+'_'+#pageable.pageNumber")
    public Page<Post> findPostByCategories(Category category, Pageable pageable) {
        return postRepository.findPostByCategoriesAndPostStatus(category, PostStatusEnum.PUBLISHED, pageable);
    }

    /**
     * 根据标签查询文章，分页
     *
     * @param tag      tag
     * @param pageable pageable
     * @return Page
     */
    @Override
    @CachePut(value = POSTS_CACHE_NAME, key = "'posts_tag_'+#tag.tagId+'_'+#pageable.pageNumber")
    public Page<Post> findPostsByTags(Tag tag, Pageable pageable) {
        return postRepository.findPostsByTagsAndPostStatus(tag, PostStatusEnum.PUBLISHED, pageable);
    }

    /**
     * 搜索文章
     *
     * @param keyword  关键词
     * @param pageable 分页信息
     * @return Page
     */
    @Override
    public Page<Post> searchByKeywords(String keyword, Pageable pageable) {
        return postRepository.findPostByPostTitleLikeOrPostContentLikeAndPostTypeAndPostStatus(keyword, pageable);
    }

    /**
     * 热门文章
     *
     * @return List
     */
    @Override
    @Cacheable(value = POSTS_CACHE_NAME, key = "'posts_hot'")
    public List<Post> hotPosts() {
        return postRepository.findPostsOrderByVisitCountDesc();
    }

    /**
     * 当前文章的相似文章
     *
     * @param post post
     * @return List
     */
    @Override
    @CachePut(value = POSTS_CACHE_NAME, key = "'posts_related_'+#post.getPostId()")
    public List<Post> relatedPosts(Post post) {
        // 获取当前文章的所有标签
        List<Tag> tags = post.getTags();
        List<Post> tempPosts = new ArrayList<>();
        for (Tag tag : tags) {
            tempPosts.addAll(postRepository.findPostsByTags(tag));
        }
        // 去掉当前的文章
        tempPosts.remove(post);
        // 去掉重复的文章
        List<Post> allPosts = new ArrayList<>();
        for (Post tempPost : tempPosts) {
            if (!allPosts.contains(tempPost)) {
                allPosts.add(tempPost);
            }
        }
        return allPosts;
    }

    /**
     * 获取所有文章的阅读量
     *
     * @return long
     */
    @Override
    public long getPostViews() {
        return postRepository.getPostViewsSum();
    }

    /**
     * 根据文章状态查询数量
     *
     * @param status 文章状态
     * @return 文章数量
     */
    @Override
    public int getCountByStatus(PostStatusEnum status) {
        return postRepository.countAllByPostStatus(status);
    }

    /**
     * 生成rss
     *
     * @param posts posts
     * @return String
     */
    @Override
    public String buildRss(List<Post> posts) {
        String rss = "";
        try {
            rss = HaloUtils.getRss(posts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rss;
    }

    /**
     * 生成siteMap
     *
     * @param posts posts
     * @return String
     */
    @Override
    public String buildSiteMap(List<Post> posts) {
        String head = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<urlset " +
                "xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">";
        String urlBody = "";
        String urlItem;
        String urlPath = optionHolder.get(OptionEnum.BLOG_URL) + "/archives/";
        for (Post post : posts) {
            urlItem = "<url><loc>" + urlPath + post.getPostUrl() + "</loc><lastmod>" + HaloUtils
                    .getStringDate(post.getPostDate(), "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") + "</lastmod>" + "</url>";
            urlBody += urlItem;
        }
        return head + urlBody + "</urlset>";
    }

}
