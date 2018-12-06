package cn.mingyuliu.halo.common;

import com.google.common.base.Joiner;
import org.springframework.data.domain.Sort;

/**
 * <pre>
 *     Halo常量
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/10/25
 */
public class HaloConst {

    public static final String SPACE = " ";
    public static final String UNDER_LINE = "_";
    public static final String COMMA = ",";
    public static final String DOT = ".";

    /**
     * 后台菜单root节点
     */
    public static byte MANAGEMENT_MENU_ROOT_ID = 1;

    /**
     * 前端菜单root节点
     */
    public static byte FRONT_MENU_ROOT_ID = 2;

    /**
     * 文章分类根节点
     */
    public static byte POST_ROOT_CATEGORY = 1;

    /**
     * 站点分类根节点
     */
    public static byte SITE_ROOT_CATEGORY = 1;

    /**
     * 文章排序
     */
    public static Sort POST_SORT = new Sort(Sort.Direction.DESC, "pubDate");

    /**
     * 逗号String链接器
     */
    public static final Joiner DOT_JOINER = Joiner.on(",");

}
