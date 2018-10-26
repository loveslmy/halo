package cn.mingyuliu.halo.common.enums;

/**
 * <pre>
 *     文章状态
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/09/02
 */
public enum PostStatus {
    /**
     * 草稿
     */
    DRAFT("草稿"),

    /**
     * 已发布
     */
    PUBLISHED("已发布"),

    /**
     * 回收站
     */
    RECYCLE("回收站");

    private String desc;

    PostStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
