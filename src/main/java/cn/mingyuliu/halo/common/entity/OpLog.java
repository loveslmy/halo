package cn.mingyuliu.halo.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * <pre>
 *     操作日志
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/02
 */
@Getter
@Setter
@Entity
@Table(name = "op_log")
public class OpLog extends BaseEntity {

    private static final long serialVersionUID = -2571815432301283171L;

    public OpLog() {

    }

    @NotBlank(message = "摘要不能为空")
    @Column(columnDefinition = "CHAR(32) NOT NULL COMMENT '摘要'")
    private String logTitle;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '内容'")
    private String logContent;

    @NotBlank(message = "产生日志ip不能为空")
    @Column(columnDefinition = "CHAR(48) COMMENT '产生日志ip'")
    private String logIp;

    public OpLog(String logTitle, String logContent, String logIp, Date logCreated) {
        this.logTitle = logTitle;
        this.logContent = logContent;
        this.logIp = logIp;
        this.crtDate = logCreated;
    }

}
