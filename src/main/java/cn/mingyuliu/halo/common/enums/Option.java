package cn.mingyuliu.halo.common.enums;

import cn.mingyuliu.halo.utils.DataType;

/**
 * <pre>
 *     博客常用设置enum
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/02
 */
public enum Option {

    /**
     * 是否已经安装
     */
    IS_INSTALL(DataType.BOOLEAN, 0),


    /**
     * 博客标题
     */
    BLOG_TITLE(DataType.STRING, 1),

    /**
     * 博客地址DataType
     */
    BLOG_URL(DataType.STRING, 2),

    /**
     * 文章摘要字数
     */
    POST_SUMMARY(DataType.INTEGER, 3),

    /**
     * 首页文章条数
     */
    POST_PAGE_COUNT(DataType.INTEGER, 4),

    /**
     * RSS显示文章条数
     */
    RSS_POSTS(DataType.INTEGER, 5),

    /**
     * API状态
     */
    API_STATUS(DataType.INTEGER, 6),

    /**
     * 邮箱服务器地址
     */
    MAIL_SMTP_HOST(DataType.STRING, 7),

    /**
     * 邮箱地址
     */
    MAIL_SMTP_USERNAME(DataType.STRING, 8),

    /**
     * 邮箱密码／授权码
     */
    MAIL_SMTP_PASSWORD(DataType.STRING, 9),

    /**
     * 发送者名称
     */
    MAIL_FROM_NAME(DataType.STRING, 10),

    /**
     * 启用邮件服务
     */
    SMTP_EMAIL_ENABLE(DataType.BOOLEAN, 11),

    /**
     * 邮件回复通知
     */
    COMMENT_REPLY_NOTICE(DataType.BOOLEAN, 12),

    /**
     * 新评论是否需要审核
     */
    NEW_COMMENT_NEED_CHECK(DataType.BOOLEAN, 13),

    /**
     * 新评论通知
     */
    NEW_COMMENT_NOTICE(DataType.BOOLEAN, 14),

    /**
     * 邮件审核通过通知
     */
    COMMENT_PASS_NOTICE(DataType.BOOLEAN, 15),

    /**
     * 站点描述
     */
    SEO_DESC(DataType.STRING, 16),

    /**
     * 博客主题
     */
    THEME(DataType.STRING, 17),

    /**
     * 博客搭建日期
     */
    BLOG_START(DataType.DATE, 18),

    /**
     * 仪表盘部件 文章总数
     */
    WIDGET_POST_COUNT(DataType.INTEGER, 19),

    /**
     * 仪表盘部件 评论总数
     */
    WIDGET_COMMENT_COUNT(DataType.INTEGER, 20),

    /**
     * 仪表盘部件 附件总数
     */
    WIDGET_ATTACHMENT_COUNT(DataType.INTEGER, 21),

    /**
     * 仪表盘部件 成立天数
     */
    WIDGET_DAY_COUNT(DataType.INTEGER, 22);

    private int code;
    private int dataType;

    private static final Option[] MAP = new Option[23];

    static {
        for (Option applicationType : Option.values()) {
            MAP[applicationType.code] = applicationType;
        }
    }

    Option(int dataType, int code) {
        this.dataType = dataType;
        this.code = code;
    }

    public static Option of(int value) {
        return MAP[value];
    }

    public int getCode() {
        return code;
    }

    public int getDataType() {
        return dataType;
    }

}
