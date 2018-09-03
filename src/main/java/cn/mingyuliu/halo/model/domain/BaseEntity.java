package cn.mingyuliu.halo.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *     实体基类
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/08/31
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    /**
     * 编号
     */
    @Id
    @GeneratedValue
    protected long id;
    @Column(columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    protected Date crtDate;
    @Column(columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP " +
            "COMMENT '最后更新时间'")
    protected Date updDate = new Date();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

}
