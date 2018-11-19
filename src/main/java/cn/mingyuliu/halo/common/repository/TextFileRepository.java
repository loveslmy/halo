package cn.mingyuliu.halo.common.repository;

import cn.mingyuliu.halo.common.entity.TextFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 *     文本文件持久层
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/11/09
 */
@Repository
public interface TextFileRepository extends JpaRepository<TextFile, Long> {
}
