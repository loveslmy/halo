package cn.mingyuliu.halo.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <pre>
 *     附件
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/02
 */
@Getter
@Setter
@Entity
@Table(name = "halo_attachment")
public class Attachment extends BaseEntity {

    private static final long serialVersionUID = 3060117944880138064L;

    /**
     * 附件名
     */
    private String attachName;

    /**
     * 附件路径
     */
    private byte[] data;

    /**
     * 附件类型
     */
    private String attachType;

    /**
     * 附件后缀
     */
    private String attachSuffix;

    /**
     * 附件大小
     */
    private String attachSize;

}
