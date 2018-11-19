package cn.mingyuliu.halo.common.repository;

import cn.mingyuliu.halo.common.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * <pre>
 *     文件持久层
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/11/09
 */
@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    /**
     * 根据md5查询文件
     *
     * @param md5 md5码
     * @return 文件实体
     */
    File findByMd5(String md5);

    /**
     * 根据文件后缀名和分页信息查询文件
     * @param suffixes 文件后缀名列表
     * @param pageable 分页数据
     * @return 分页文件
     */
    Page<File> findBySuffixIn(Set<String> suffixes , Pageable pageable);

}
