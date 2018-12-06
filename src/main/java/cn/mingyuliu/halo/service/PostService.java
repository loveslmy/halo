package cn.mingyuliu.halo.service;

import cn.mingyuliu.halo.common.entity.Category;
import cn.mingyuliu.halo.common.entity.Post;
import cn.mingyuliu.halo.common.entity.Tag;
import cn.mingyuliu.halo.common.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <pre>
 *     文章/页面业务逻辑接口
 * </pre>
 *
 * @author : RYAN0UP
 * @since : 2017/11/14
 */
public interface PostService {

    /**
     * 新增文章
     *
     * @param post Post
     * @return Post
     */
    Post saveByPost(Post post);

    /**
     * 根据编号删除文章
     *
     * @param postId postId
     * @return Post
     */
    Post removeByPostId(long postId);

    /**
     * 修改文章状态
     *
     * @param postId postId
     * @param status status
     * @return Post
     */
    Post updatePostStatus(long postId, PostStatus status);

    /**
     * 修改文章阅读量
     *
     * @param post post
     */
    void updatePostView(Post post);

    /**
     * 批量修改摘要
     *
     * @param postSummary postSummary
     */
    void updateAllSummary(int postSummary);

    /**
     * 获取文章列表 分页
     *
     * @param pageable 分页信息
     * @return Page
     */
    Page<Post> findAllPosts(Pageable pageable);

    /**
     * 获取文章列表 不分页
     *
     * @return List
     */
    List<Post> findAllPosts();

    /**
     * 模糊查询文章
     *
     * @param keyWord  keyword
     * @param pageable pageable
     * @return List
     */
    List<Post> searchPosts(String keyWord, Pageable pageable);

    /**
     * 根据文章状态查询 分页，用于后台管理
     *
     * @param status   0，1，2
     * @param pageable 分页信息
     * @return Page
     */
    Page<Post> findPostByStatus(PostStatus status, Pageable pageable);

    /**
     * 根据文章状态查询 分页，首页分页
     *
     * @param pageable pageable
     * @return Page
     */
    Page<Post> findPostByStatus(Pageable pageable);

    /**
     * 根据文章状态查询
     *
     * @param status 0，1，2
     * @return List
     */
    List<Post> findPostByStatus(PostStatus status);

    /**
     * 根据编号查询文章
     *
     * @param postId postId
     * @return Post
     */
    Optional<Post> findByPostId(long postId);

    /**
     * 查询前五条数据
     *
     * @return List
     */
    List<Post> findPostLatest();

    /**
     * 查询Id之后的文章
     *
     * @param postDate postDate
     * @return List
     */
    List<Post> findByPostDateAfter(Date postDate);

    /**
     * 查询Id之前的文章
     *
     * @param postDate postDate
     * @return List
     */
    List<Post> findByPostDateBefore(Date postDate);

    /**
     * 根据年份和月份查询文章
     *
     * @param year  year
     * @param month month
     * @return List
     */
    List<Post> findPostByYearAndMonth(String year, String month);

    /**
     * 根据年份和月份查询文章 分页
     *
     * @param year     year
     * @param month    month
     * @param pageable pageable
     * @return Page
     */
    Page<Post> findPostByYearAndMonth(String year, String month, Pageable pageable);

    /**
     * 根据年份查询文章
     *
     * @param year year
     * @return List
     */
    List<Post> findPostByYear(String year);

    /**
     * 根据分类目录查询文章
     *
     * @param category category
     * @param pageable pageable
     * @return Page
     */
    Page<Post> findPostByCategories(Category category, Pageable pageable);

    /**
     * 根据标签查询文章
     *
     * @param tag      tag
     * @param pageable pageable
     * @return Page
     */
    Page<Post> findPostsByTags(Tag tag, Pageable pageable);

    /**
     * 搜索文章
     *
     * @param keyword  关键词
     * @param pageable 分页信息
     * @return Page
     */
    Page<Post> searchByKeywords(String keyword, Pageable pageable);

    /**
     * 热门文章
     *
     * @return List
     */
    List<Post> hotPosts();

    /**
     * 当前文章的相似文章
     *
     * @param post post
     * @return List
     */
    List<Post> relatedPosts(Post post);

    /**
     * 获取所有文章的阅读量
     *
     * @return long
     */
    long getPostViews();

    /**
     * 根据文章状态查询数量
     *
     * @param status 文章状态
     * @return 文章数量
     */
    int getCountByStatus(PostStatus status);

}
