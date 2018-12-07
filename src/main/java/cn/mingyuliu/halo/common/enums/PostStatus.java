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
    DRAFT(0),

    /**
     * 已发布
     */
    PUBLISHED(1),

    /**
     * 回收站
     */
    RECYCLE(2);

    private static final PostStatus[] MAP = new PostStatus[3];

    static {
        for (PostStatus postStatus : PostStatus.values()) {
            MAP[postStatus.value] = postStatus;
        }
    }

    private byte value;

    PostStatus(int postStatus) {
        value = (byte) postStatus;
    }

    public static PostStatus of(int value) {
        return MAP[value];
    }

}
