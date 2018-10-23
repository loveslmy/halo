package cn.mingyuliu.halo.common.dto;

import com.google.common.base.Joiner;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     公共常量
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2017/12/29
 */
public class HaloConst {
    /**
     * 文章排序
     */
    public static Sort POST_SORT = new Sort(Sort.Direction.DESC, "postDate");
    /**
     * 逗号String链接器
     */
    public static final Joiner DOT_JOINER = Joiner.on(",");
    /**
     * 默认分页记录数
     */
    public static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * 默认category url
     */
    public static final String DEFAULT_CATEGORY_URL = "all";

    /**
     * OwO表情
     */
    public static Map<String, String> OWO = new HashMap<>();

    /**
     * 所有主题
     */
    public static List<Theme> THEMES = new ArrayList<>();

    /**
     * user_session
     */
    public static String USER_SESSION_KEY = "user_session";


}
