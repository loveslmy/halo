package cn.mingyuliu.halo.service.impl;

import cn.mingyuliu.halo.common.entity.OpLog;
import cn.mingyuliu.halo.common.repository.LogsRepository;
import cn.mingyuliu.halo.service.LogsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * <pre>
 *     日志业务逻辑实现类
 * </pre>
 *
 * @author : RYAN0UP
 * @since : 2018/1/19
 */
@Service
public class LogsServiceImpl implements LogsService {

    @Resource
    private LogsRepository logsRepository;

    /**
     * 保存日志
     *
     * @param opLog opLog
     * @return OpLog
     */
    @Override
    public OpLog saveByLogs(OpLog opLog) {
        return logsRepository.save(opLog);
    }

    /**
     * 根据编号移除
     *
     * @param logsId logsId
     */
    @Override
    public void removeByLogsId(Long logsId) {
        Optional<OpLog> logs = this.findLogsByLogsId(logsId);
        logsRepository.delete(logs.get());
    }

    /**
     * 移除所有日志
     */
    @Override
    public void removeAllLogs() {
        logsRepository.deleteAll();
    }

    /**
     * 查询所有日志并分页
     *
     * @param pageable pageable
     * @return Page
     */
    @Override
    public Page<OpLog> findAllLogs(Pageable pageable) {
        return logsRepository.findAll(pageable);
    }

    /**
     * 查询最新的五条日志
     *
     * @return List
     */
    @Override
    public List<OpLog> findLogsLatest() {
        return logsRepository.findTopFive();
    }

    /**
     * 根据编号查询
     *
     * @param logsId logsId
     * @return Optional
     */
    @Override
    public Optional<OpLog> findLogsByLogsId(Long logsId) {
        return logsRepository.findById(logsId);
    }
}
