package cn.mingyuliu.halo.model.dto;

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

    public static Sort POST_SORT = new Sort(Sort.Direction.DESC, "postDate");
    public static final Joiner DOT_JOINER = Joiner.on(",");
    public static final int DEFAULT_PAGE_SIZE = 10;


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
