package cn.mingyuliu.halo.common.repository;

import cn.mingyuliu.halo.common.entity.OpLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * <pre>
 *     日志持久层
 * </pre>
 *
 * @author : RYAN0UP
 * @since : 2018/1/19
 */
public interface LogsRepository extends JpaRepository<OpLog, Long> {

    /**
     * 查询最新的五条数据
     *
     * @return List
     */
    @Query(value = "SELECT * FROM halo_logs ORDER BY log_created DESC LIMIT 5", nativeQuery = true)
    List<OpLog> findTopFive();
}
