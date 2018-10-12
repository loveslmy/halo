package cn.mingyuliu.halo.common.repository;

import cn.mingyuliu.halo.common.entity.Category;
import cn.mingyuliu.halo.common.entity.Post;
import cn.mingyuliu.halo.common.entity.Tag;
import cn.mingyuliu.halo.common.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *     文章持久层
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2017/11/14
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 查询前五条文章
     *
     * @return List
     */
    @Query(value = "SELECT * FROM halo_post ORDER BY post_date DESC LIMIT 5", nativeQuery = true)
    List<Post> findTopFive();

    /**
     * 模糊查询
     *
     * @param keyWord  keyword
     * @param pageable pageable
     * @return List
     */
    List<Post> findByPostTitleLike(String keyWord, Pageable pageable);

    /**
     * 根据文章的状态查询 分页
     *
     * @param status   0，1，2
     * @param pageable 分页信息
     * @return Page
     */
    Page<Post> findPostsByPostStatus(PostStatus status, Pageable pageable);

    /**
     * 根据文章的状态查询
     *
     * @param status 0,1,2
     * @return List
     */
    List<Post> findPostsByPostStatus(PostStatus status);

    /**
     * 根据路径查询文章
     *
     * @param postUrl 路径
     * @return Post
     */
    Post findPostByPostUrl(String postUrl);

    /**
     * 查询之后文章
     *
     * @param postDate 发布时间
     * @param status   {@link PostStatus}
     * @return List
     */
    List<Post> findByPostDateAfterAndPostStatusOrderByPostDateDesc(Date postDate, PostStatus status);


    /**
     * 查询之前的文章
     *
     * @param postDate 发布时间
     * @param status   {@link PostStatus}
     * @return List
     */
    List<Post> findByPostDateBeforeAndPostStatusOrderByPostDateAsc(Date postDate, PostStatus status);

    /**
     * 查询文章归档信息 根据年份和月份
     *
     * @return List
     */
    @Query(value = "select year(post_date) as year,month(post_date) as month,count(*) as count from halo_post where post_status=0 and post_type='post' group by year(post_date),month(post_date) order by year desc,month desc", nativeQuery = true)
    List<Object[]> findPostGroupByYearAndMonth();

    /**
     * 查询文章归档信息 根据年份
     *
     * @return List
     */
    @Query(value = "select year(post_date) as year,count(*) as count from halo_post where post_status=0 and post_type='post' group by year(post_date) order by year desc", nativeQuery = true)
    List<Object[]> findPostGroupByYear();

    /**
     * 根据年份和月份查询文章
     *
     * @param year  year
     * @param month month
     * @return List
     */
    @Query(value = "select *,year(post_date) as year,month(post_date) as month from halo_post where post_status=0 and post_type='post' and year(post_date)=:year and month(post_date)=:month order by post_date desc", nativeQuery = true)
    List<Post> findPostByYearAndMonth(@Param("year") String year, @Param("month") String month);

    /**
     * 根据年份查询文章
     *
     * @param year year
     * @return List
     */
    @Query(value = "select *,year(post_date) as year from halo_post where post_status=0 and post_type='post' and year(post_date)=:year order by post_date desc", nativeQuery = true)
    List<Post> findPostByYear(@Param("year") String year);

    /**
     * 根据年份和月份查询文章 分页
     *
     * @param year     year
     * @param month    month
     * @param pageable pageable
     * @return Page
     */
    @Query(value = "select * from halo_post where post_status=0 and post_type='post' and year(post_date)=:year and month(post_date)=:month order by post_date desc", countQuery = "select count(*) from halo_post where post_status=0 and year(post_date)=:year and month(post_date)=:month", nativeQuery = true)
    Page<Post> findPostByYearAndMonth(@Param("year") String year, @Param("month") String month, Pageable pageable);

    /**
     * 根据分类目录查询文章
     *
     * @param category category
     * @param status   {@link PostStatus}
     * @param pageable pageable
     * @return Page
     */
    Page<Post> findPostByCategoriesAndPostStatus(Category category, PostStatus status, Pageable pageable);

    /**
     * 根据标签查询文章，分页
     *
     * @param tag      tag
     * @param status   {@link PostStatus}
     * @param pageable pageable
     * @return Page
     */
    Page<Post> findPostsByTagsAndPostStatus(Tag tag, PostStatus status, Pageable pageable);

    /**
     * 根据标签查询文章
     *
     * @param tag tag
     * @return List
     */
    List<Post> findPostsByTags(Tag tag);

    /**
     * 模糊查询文章
     *
     * @param keyword  关键词
     * @param pageable 分页信息
     * @return Page
     */
    @Query(value = "select * from halo_post where post_status = 0 and post_type='post' and post_title like '%=:keyword%' or post_content like '%=:keyword%'", nativeQuery = true)
    Page<Post> findPostByPostTitleLikeOrPostContentLikeAndPostTypeAndPostStatus(String keyword, Pageable pageable);

    /**
     * 按热度从大到小排序
     *
     * @return List<Post>
     */
    @Query(value = "select * from halo_post order by visit_count desc", nativeQuery = true)
    List<Post> findPostsOrderByVisitCountDesc();

    /**
     * 获取所有文章阅读量总和
     *
     * @return Long
     */
    @Query(value = "select sum(post_views) from halo_post", nativeQuery = true)
    long getPostViewsSum();

    /**
     * 根据文章状态查询数量
     *
     * @param status 文章状态
     * @return 文章数量
     */
    int countAllByPostStatus(PostStatus status);

}
