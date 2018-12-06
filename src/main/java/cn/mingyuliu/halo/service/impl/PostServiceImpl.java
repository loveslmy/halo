package cn.mingyuliu.halo.service.impl;
import cn.mingyuliu.halo.common.entity.Category;
import cn.mingyuliu.halo.common.entity.Post;
import cn.mingyuliu.halo.common.entity.Tag;
import cn.mingyuliu.halo.common.enums.PostStatus;
import cn.mingyuliu.halo.common.repository.PostRepository;
import cn.mingyuliu.halo.config.sys.OptionHolder;
import cn.mingyuliu.halo.service.PostService;
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
 * @since : 2017/11/14
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
    public Post updatePostStatus(long postId, PostStatus status) {
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
        return postRepository.findByNameLike(keyWord, pageable);
    }

    /**
     * 根据文章状态查询 分页，用于后台管理
     *
     * @param status   0，1，2
     * @param pageable 分页信息
     * @return Page
     */
    @Override
    public Page<Post> findPostByStatus(PostStatus status, Pageable pageable) {
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
        return postRepository.findPostsByPostStatus(PostStatus.PUBLISHED, pageable);
    }

    /**
     * 根据文章状态查询
     *
     * @param status 0，1，2
     * @return List
     */
    @Override
    @Cacheable(value = POSTS_CACHE_NAME, key = "'posts_status_type_'+#status")
    public List<Post> findPostByStatus(PostStatus status) {
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
        return postRepository.findByPubDateAfterAndPostStatusOrderByPubDateDesc(postDate, PostStatus.PUBLISHED);
    }

    /**
     * 查询Id之前的文章
     *
     * @param postDate 发布时间
     * @return List
     */
    @Override
    public List<Post> findByPostDateBefore(Date postDate) {
        return postRepository.findByPubDateBeforeAndPostStatusOrderByPubDateAsc(postDate, PostStatus.PUBLISHED);
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
        return postRepository.findPostByCategoriesAndPostStatus(category, PostStatus.PUBLISHED, pageable);
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
        return postRepository.findPostsByTagsAndPostStatus(tag, PostStatus.PUBLISHED, pageable);
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
    public int getCountByStatus(PostStatus status) {
        return postRepository.countAllByPostStatus(status);
    }

}
