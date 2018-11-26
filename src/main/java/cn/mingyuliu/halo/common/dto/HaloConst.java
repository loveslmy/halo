package cn.mingyuliu.halo.common.dto;

import com.google.common.base.Joiner;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     Halo常量
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/10/25
 */
public class HaloConst {

    /**
     * 后台菜单root节点
     */
    public static long MANAGEMENT_MENU_ROOT_ID = -1L;

    /**
     * 前端菜单root节点
     */
    public static long FRONT_MENU_ROOT_ID = 0L;

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
     * user_session
     */
    public static String USER_SESSION_KEY = "user_session";


}
