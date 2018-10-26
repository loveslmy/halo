package cn.mingyuliu.halo.service;

import cn.mingyuliu.halo.common.entity.OpLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * <pre>
 *     日志业务逻辑接口
 * </pre>
 *
 * @author : RYAN0UP
 * @since : 2018/1/19
 */
public interface LogsService {

    /**
     * 保存日志
     *
     * @param opLog opLog
     * @return OpLog
     */
    OpLog saveByLogs(OpLog opLog);

    /**
     * 根据编号移除
     *
     * @param logsId logsId
     */
    void removeByLogsId(Long logsId);

    /**
     * 移除所有日志
     */
    void removeAllLogs();

    /**
     * 查询所有日志并分页
     *
     * @param pageable pageable
     * @return Page
     */
    Page<OpLog> findAllLogs(Pageable pageable);

    /**
     * 查询最新的五条日志
     *
     * @return List
     */
    List<OpLog> findLogsLatest();

    /**
     * 根据编号查询
     *
     * @param logsId logsId
     * @return Optional
     */
    Optional<OpLog> findLogsByLogsId(Long logsId);
}
