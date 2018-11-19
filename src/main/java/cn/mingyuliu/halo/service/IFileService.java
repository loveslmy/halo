package cn.mingyuliu.halo.service;

import cn.mingyuliu.halo.common.entity.File;
import cn.mingyuliu.halo.common.entity.Site;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <pre>
 *     文件服务接口
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/11/09
 */
public interface IFileService {

    /**
     * 填充站点引用图片文件
     * @param sites 站点列表
     */
    void fillFile(List<Site> sites);

    /**
     * 填充文件内容
     *
     * @param files 文件列表
     */
    void fillContent(List<File> files);

    /**
     * 根据id列表加载文件
     *
     * @param id 文件id
     * @return {@link List< File >}
     */
    File findFileById(long id);

    /**
     * 根据文件名称查找文件返回分页文件信息
     *
     * @param keyWorld 关键字
     * @return @return {@link Page<File>}
     */
    Page<File> findFileList(String keyWorld);

    /**
     * 新增/修改文件
     *
     * @param file 文件实体
     * @return 持久化以后的文件
     */
    File saveOrModify(File file);


}
