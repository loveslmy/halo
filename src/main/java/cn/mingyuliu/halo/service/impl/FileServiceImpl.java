package cn.mingyuliu.halo.service.impl;

import cn.mingyuliu.halo.common.entity.BinaryFile;
import cn.mingyuliu.halo.common.entity.File;
import cn.mingyuliu.halo.common.entity.Site;
import cn.mingyuliu.halo.common.entity.TextFile;
import cn.mingyuliu.halo.common.enums.FileType;
import cn.mingyuliu.halo.common.repository.BinaryFileRepository;
import cn.mingyuliu.halo.common.repository.FileRepository;
import cn.mingyuliu.halo.common.repository.TextFileRepository;
import cn.mingyuliu.halo.service.IFileService;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * <pre>
 *     文件服务实现类
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/11/09
 */
@Service
public class FileServiceImpl implements IFileService {

    @Resource
    private FileRepository fileRepository;

    @Resource
    private TextFileRepository textFileRepository;

    @Resource
    private BinaryFileRepository binaryFileRepository;

    /**
     * (non-Javadoc)
     *
     * @see IFileService#fillFile(List)
     */
    @Override
    public void fillFile(List<Site> sites) {
        if (CollectionUtils.isEmpty(sites)) {
            return;
        }

        Long2ObjectMap<List<Site>> refMap = new Long2ObjectLinkedOpenHashMap<>();
        for (Site site : sites) {
            Long refId = site.getRefId();
            if (null != refId) {
                refMap.computeIfAbsent(refId, v -> Lists.newLinkedList()).add(site);
            }
        }

        if (CollectionUtils.isEmpty(refMap.keySet())) {
            return;
        }

        List<File> files = fileRepository.findAllById(refMap.keySet());
        fillContent(files);
        for (File file : files) {
            List<Site> refSites = refMap.get(file.getId());
            for (Site site : refSites) {
                site.setFile(file);
            }
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see IFileService#fillContent(File)
     */
    @Override
    public void fillContent(File file) {
        if (FileType.TEXT == file.getFileType()) {
            Optional<TextFile> textFileOpt = textFileRepository.findById(file.getRefId());
            if (textFileOpt.isPresent()) {
                file.setContent(textFileOpt.get().getContent());
            }
        } else {
            Optional<BinaryFile> binaryFileOpt = binaryFileRepository.findById(file.getRefId());
            if (binaryFileOpt.isPresent()) {
                file.setDatas(binaryFileOpt.get().getDatas());
            }
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see IFileService#fillContent(List)
     */
    @Override
    public void fillContent(List<File> files) {
        if (CollectionUtils.isEmpty(files)) {
            return;
        }
        Long2ObjectMap<File> textFileMap = new Long2ObjectLinkedOpenHashMap<>();
        Long2ObjectMap<File> binaryFileMap = new Long2ObjectLinkedOpenHashMap<>();
        for (File file : files) {
            if (FileType.TEXT == file.getFileType()) {
                textFileMap.put(file.getRefId(), file);
            } else {
                binaryFileMap.put(file.getRefId(), file);
            }
        }

        if (!textFileMap.isEmpty()) {
            List<TextFile> textFiles = textFileRepository.findAllById(textFileMap.keySet());
            for (TextFile textFile : textFiles) {
                textFileMap.get(textFile.getId()).setContent(textFile.getContent());
            }
        }

        if (!binaryFileMap.isEmpty()) {
            List<BinaryFile> binaryFiles = binaryFileRepository.findAllById(binaryFileMap.keySet());
            for (BinaryFile binaryFile : binaryFiles) {
                textFileMap.get(binaryFile.getId()).setDatas(binaryFile.getDatas());
            }
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see IFileService#findFileById(long)
     */
    @Override
    public File findFileById(long id) {
        Optional<File> fileOpt = fileRepository.findById(id);
        if (!fileOpt.isPresent()) {
            return null;
        }
        File file = fileOpt.get();
        if (FileType.TEXT == file.getFileType()) {
            TextFile textFile = textFileRepository.getOne(file.getRefId());
            file.setContent(textFile.getContent());
            return file;
        }

        BinaryFile binaryFile = binaryFileRepository.getOne(file.getRefId());
        file.setDatas(binaryFile.getDatas());
        return file;
    }

    /**
     * (non-Javadoc)
     *
     * @see IFileService#findFileList(String)
     */
    @Override
    public Page<File> findFileList(String keyWorld) {
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see IFileService#saveOrModify(File)
     */
    @Override
    @Transactional
    public File saveOrModify(File file) {
        long refId;
        if (FileType.TEXT == file.getFileType()) {
            TextFile textFile = new TextFile();
            textFile.setContent(file.getContent());
            refId = textFileRepository.save(textFile).getId();
        } else {
            BinaryFile binaryFile = new BinaryFile();
            binaryFile.setDatas(file.getDatas());
            refId = binaryFileRepository.save(binaryFile).getId();
        }

        file.setRefId(refId);
        return fileRepository.save(file);
    }

}
